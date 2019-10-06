package cn.boosp.sharebook.controller.api;

import cn.boosp.sharebook.common.ApiResponse;
import cn.boosp.sharebook.common.dto.BookDTO;
import cn.boosp.sharebook.common.pojo.Gbook;
import cn.boosp.sharebook.service.GbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/gbook")
public class GbookController {
    @Autowired
    GbookService gbookService;

    @GetMapping("/isbn/{isbn13}")
    public ApiResponse getGbook(@PathVariable(name = "isbn13") String isbn13) {
        Gbook.VolumeInfo gbook = gbookService.getGbook(isbn13);
        if (null != gbook) {
            return new ApiResponse(ApiResponse.Status.ok, new BookDTO(gbook));
        } else {
            return new ApiResponse(ApiResponse.Status.error, null, new ApiResponse.ApiError(400, "抱歉，未查询到该书信息"));
        }
    }

    @GetMapping("/isbn/{isbn13}/image")
    public void getImage(@PathVariable String isbn13, HttpServletResponse response) {

    }
}
