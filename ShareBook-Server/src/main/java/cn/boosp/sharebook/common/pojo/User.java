package cn.boosp.sharebook.common.pojo;

import cn.boosp.sharebook.common.dto.UserDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String phone;

    private String password;

    private Boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_ubook",
            joinColumns = {
                    @JoinColumn(
                            name = "user_id")},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "ubook_id")})
    private List<Ubook> ubooks;
    /**
     *
     */
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_book",
            joinColumns = {@JoinColumn(
                    name = "user_id")},
            inverseJoinColumns = {@JoinColumn(
                    name = "book_id")})
    private List<Book> books;

    //最近联系人
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private List<Contact> contacts;
    /**
     * 1.CascadeType.PERSIST 级联创建
     * 2.CascadeType.ALL 级联删除
     * 3.CascadeType.REFRESH 级联刷新
     * 4.CascadeType.MERGE 级联更新
     * 5.CascadeType.ALL 四项全选
     */
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Order> orders;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")})
    private List<Role> roles;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_wallet",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "wallet_id"))
    private Wallet wallet;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.createDate = new Date(System.currentTimeMillis());
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createDate = new Date(System.currentTimeMillis());
    }

    public User() {
        createDate = new Date(System.currentTimeMillis());
    }

    public User(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.phone = userDTO.getPhone();
        this.password = userDTO.getPassword();
        this.createDate = new Date(System.currentTimeMillis());
    }

    public User(Integer userId) {
        this.id = userId;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<Ubook> getUbooks() {
        return ubooks;
    }

    public void setUbooks(List<Ubook> ubooks) {
        this.ubooks = ubooks;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date crateDate) {
        this.createDate = crateDate;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


}
