package com.gddlkj.example.web.rest.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gddlkj.example.model.constants.ResponseCodeEnum;
import com.gddlkj.example.model.domain.Dictionary;
import com.gddlkj.example.model.dto.MyPage;
import com.gddlkj.example.model.dto.R;
import com.gddlkj.example.service.IDictionaryService;
import com.gddlkj.example.web.rest.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author blank
 * @since 2019-01-24
 */
@RestController
@Slf4j
@Api(tags = "数据字典")
@RequestMapping("admin/dictionary")
public class DictionaryController extends BaseController {

    @Resource
    private IDictionaryService dictionaryService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping()
    @ApiOperation(value = "分页查询数据字典")
    public R<IPage<Dictionary>> listByPage(MyPage<Dictionary> myPage, Long pid) {
        return success(dictionaryService.page(myPage.convertToPage(), Wrappers.<Dictionary>lambdaQuery().eq(Dictionary::getPid, pid)));
    }

    @GetMapping("types")
    @ApiOperation(value = "查询字典分类")
    public R<List<Dictionary>> listTypes() {
        return success(dictionaryService.list(Wrappers.<Dictionary>lambdaQuery().eq(Dictionary::getPid, 0)));
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取数据字典")
    public R<Dictionary> selectById(@PathVariable Long id) {
        return success(dictionaryService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除数据字典")
    public R delete(@PathVariable Long id) {
        Dictionary dictionary = dictionaryService.getById(id);
        // 清空缓存
        String type = dictionaryService.getById(dictionary.getPid()).getName();
        redisTemplate.delete("dictionary:" + type + ":name:" + dictionary.getName());
        redisTemplate.delete("dictionary:" + type + ":value:" + dictionary.getValue());
        return success(dictionaryService.removeById(id));
    }

    @PutMapping()
    @ApiOperation(value = "更新一条数据字典记录")
    public R update(@RequestBody Dictionary dictionary) {
        if (dictionary.getId() == null) {
            return error(ResponseCodeEnum.NOT_FOUND);
        }
        if (dictionaryService.count(Wrappers.<Dictionary>lambdaQuery()
                .eq(Dictionary::getName, dictionary.getName())
                .eq(Dictionary::getPid, dictionary.getPid())
                .ne(Dictionary::getId, dictionary.getId())) > 0) {
            return error(ResponseCodeEnum.DICTIONARY_REPEAT);
        }
        String type = dictionaryService.getById(dictionary.getPid()).getName();
        Dictionary redisObject = dictionaryService.getById(dictionary.getId());
        redisTemplate.delete("dictionary:" + type + ":name:" + redisObject.getName());
        redisTemplate.delete("dictionary:" + type + ":value:" + redisObject.getValue());
        return success(dictionaryService.updateById(dictionary));
    }

    @PostMapping()
    @ApiOperation(value = "新增一条数据字典记录")
    public R save(@RequestBody Dictionary dictionary) {
        if (dictionaryService.count(Wrappers.<Dictionary>lambdaQuery()
                .eq(Dictionary::getName, dictionary.getName())
                .eq(Dictionary::getPid, dictionary.getPid())) > 0) {
            return error(ResponseCodeEnum.DICTIONARY_REPEAT);
        }
        return success(dictionaryService.save(dictionary));
    }


}

