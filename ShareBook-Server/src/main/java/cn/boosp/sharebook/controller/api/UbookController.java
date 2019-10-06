package cn.boosp.sharebook.controller.api;

import cn.boosp.sharebook.common.ApiResponse;
import cn.boosp.sharebook.common.ApiResponse.Status;
import cn.boosp.sharebook.common.dto.UbookDTO;
import cn.boosp.sharebook.common.exception.BookNotFoundException;
import cn.boosp.sharebook.common.exception.UbookNotFoundException;
import cn.boosp.sharebook.common.exception.UserNotFoundException;
import cn.boosp.sharebook.common.pojo.Gbook;
import cn.boosp.sharebook.common.pojo.Gbook.ImageLinks;
import cn.boosp.sharebook.common.pojo.Ubook;
import cn.boosp.sharebook.service.BookService;
import cn.boosp.sharebook.service.GbookService;
import cn.boosp.sharebook.service.UbookService;
import cn.boosp.sharebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static cn.boosp.sharebook.common.pojo.Ubook.Status.*;

@RestController
@RequestMapping("/api/ubook")
public class UbookController {
    @Autowired
    UbookService ubookService;
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    GbookService gbookService;

    @GetMapping("")
    public ApiResponse list(Integer pageIndex, Integer pageSize) {
        Page<Ubook> ubooks = ubookService.listUbook(pageIndex, pageSize);
        List<UbookDTO> ubookDTOS = getUbookDTOS(ubooks);
        return new ApiResponse(Status.ok, ubookDTOS, ubooks.getTotalElements(), ubooks.getTotalPages());
    }

    /**
     * 发布成功后,返回给客户端id.
     *
     * @param ubookDTO
     * @return
     */
    @PostMapping("")
    public ApiResponse add(@RequestBody UbookDTO ubookDTO) {

        Integer id = null;
        try {

            id = ubookService.addUbook(getUbookfromDTO(ubookDTO));
            if (id > 0) {
                return new ApiResponse(Status.ok, id);
            } else {
                return new ApiResponse(Status.error, null, new ApiResponse.ApiError(400, "添加失败"));
            }
        } catch (BookNotFoundException | UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiResponse.ApiError(404, e.getMessage()));
        }

    }

    private List<UbookDTO> getUbookDTOS(Iterable<Ubook> ubooks) {
        List<UbookDTO> ubookDTOS = new ArrayList<>();
        for (Ubook ubook : ubooks) {
            ubookDTOS.add(new UbookDTO(ubook));
        }
        return ubookDTOS;
    }


    @GetMapping("/{username}/count")
    public ApiResponse getUserUbookCount(@PathVariable("username") String username) {
        Integer count = null;
        try {
            count = ubookService.getCountByUsername(username);
            return new ApiResponse(Status.ok, count);
        } catch (UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiResponse.ApiError(404, e.getMessage()));
        }

    }

    @GetMapping("/{username}/isbn")
    public ApiResponse getUserUbooksIsbn(@PathVariable("username") String username) {
        Set<String> ubookIsbns = null;
        try {
            ubookIsbns = ubookService.findUbookIsbns(username);
            return new ApiResponse(Status.ok, ubookIsbns);
        } catch (UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiResponse.ApiError(400, e.getMessage()));
        }
    }

    @GetMapping("/{username}/release")
    public ApiResponse getUbooksByUsername(@PathVariable("username") String username) {
        List<Ubook> ubooks = null;
        try {
            ubooks = ubookService.findAllByUsername(username);
            List<UbookDTO> ubookDTOS = getUbookDTOS(ubooks);
            return new ApiResponse(Status.ok, ubookDTOS);
        } catch (UserNotFoundException e) {
            ApiResponse apiResponse = new ApiResponse(Status.error, -1, new ApiResponse.ApiError(400, e.getMessage()));
            return apiResponse;
        }
    }

    @PostMapping("/{username}/some")
    public ApiResponse getUbooksByIsbns(@RequestBody List<String> isbns) {
        List<Ubook> ubooks = ubookService.findAll(isbns);
        List<UbookDTO> ubookDTOS = getUbookDTOS(ubooks);
        return new ApiResponse(Status.ok, ubookDTOS);
    }

    private List<UbookDTO> getUbookDTOS(List<Ubook> ubooks) {
        List<UbookDTO> ubookDTOS = new ArrayList<>();
        for (Ubook ubook : ubooks) {
            ubookDTOS.add(new UbookDTO(ubook));
        }
        return ubookDTOS;
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        Ubook ubook = null;
        try {
            ubook = ubookService.findUbookById(id);
        } catch (UbookNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiResponse.ApiError(404, e.getMessage()));
        }
        return new ApiResponse(Status.ok, new UbookDTO(ubook));
    }

    @GetMapping("/{id}/before")
    public ApiResponse getBefore(@PathVariable Integer id, Integer limit) {
        List<Ubook> ubooks = ubookService.getBefore(id, limit);
        return new ApiResponse(Status.ok, getUbookDTOS(ubooks));
    }

    @GetMapping("/{id}/after")
    public ApiResponse getAfter(@PathVariable Integer id, Integer limit) {
        List<Ubook> ubooks = ubookService.getAfter(id, limit);
        return new ApiResponse(Status.ok, getUbookDTOS(ubooks));
    }

    @GetMapping("/top")
    public ApiResponse getTop(Integer limit) {
        Page<Ubook> ubooks = ubookService.getTop(limit);
        return new ApiResponse(Status.ok, getUbookDTOS(ubooks));
    }



    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable("id") Integer id) {
        ubookService.deleteUbookById(id);
        return new ApiResponse(Status.ok, null);
    }

    @PutMapping
    public ApiResponse update(@RequestBody @Valid UbookDTO ubookDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ApiResponse(Status.error, null, new ApiResponse.ApiError(400, "数据填写不完整,更新错误"));
        }
        Ubook ubook = null;
        try {
            ubook = ubookService.updateUbook(getUbookfromDTO(ubookDTO));
            return new ApiResponse(Status.ok, ubook);
        } catch (UbookNotFoundException | UserNotFoundException | BookNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiResponse.ApiError(404, e.getMessage()));
        }

    }

    private Ubook getUbookfromDTO(UbookDTO ubookDTO) throws BookNotFoundException, UserNotFoundException {
        Ubook ubook = new Ubook();
        ubook.setReleaseTime(new Date(System.currentTimeMillis()));
        ubook.setStatus(valueOf(ubookDTO.getStatus()));
        ubook.setBookIntro(ubookDTO.getBookIntro());
        ubook.setRentPrice(ubookDTO.getRentPrice());
        ubook.setSellPrice(ubookDTO.getSellPrice());
        ubook.setReleaseTime(ubookDTO.getReleaseTime());
        ubook.setIsbn13(ubookDTO.getIsbn13());
        String type = ubookDTO.getType();
        ubook.setType(type);
        if(type == "book"){
            ubook.setBook(bookService.getBookById(ubookDTO.getBookId()));
        }else {
            ubook.setVolumeInfo(gbookService.getGbook(ubookDTO.getIsbn13()));
        }
        ImageLinks imageLinks = ubook.getVolumeInfo().getImageLinks();
        if(imageLinks != null){
            ubook.setImage(imageLinks.getSmallThumbnail());
        }
        ubook.setUser(userService.findUserByUsername(ubookDTO.getUsername()));
        return ubook;
    }
}
