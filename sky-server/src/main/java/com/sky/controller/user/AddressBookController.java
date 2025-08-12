package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Tag(name = "C端-地址簿接口")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    @Operation(summary = "查询当前登录用户的所有地址信息")
    public Result<List<AddressBook>> list(){
        log.info("查询当前登录用户的所有地址信息");
        List<AddressBook> list = addressBookService.list();
        return Result.success(list);
    }

    @PostMapping
    @Operation(summary = "新增地址")
    public Result save(@RequestBody AddressBook addressBook){
        log.info("新增地址：{}", addressBook);
        addressBookService.save(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询地址")
    public Result<AddressBook> getById(@PathVariable Long id){
        log.info("根据id查询地址：{}", id);
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @PutMapping
    @Operation(summary = "根据id修改地址")
    public Result update(@RequestBody AddressBook addressBook){
        log.info("根据id修改地址：{}", addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }

    @PutMapping("/default")
    @Operation(summary = "设置默认地址")
    public Result setDefault(@RequestBody AddressBook addressBook){
        log.info("设置默认地址：{}", addressBook);
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    @DeleteMapping("/")
    @Operation(summary = "根据id删除地址")
    public Result deleteById(Long id){
        log.info("根据id删除地址：{}", id);
        addressBookService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/default")
    @Operation(summary = "查询默认地址")
    public Result<AddressBook> getDefault(){
        log.info("查询默认地址");
        List<AddressBook> addressBook = addressBookService.getDefault();
        if(addressBook != null && !addressBook.isEmpty()){
            return Result.success(addressBook.getFirst());
        }
        return Result.error("没有查询到默认地址");
    }
}
