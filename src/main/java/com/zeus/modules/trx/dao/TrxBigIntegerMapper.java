package com.zeus.modules.trx.dao;

import com.zeus.modules.trx.entity.TrxBigInteger;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author muzi
 * @since 2023-04-09
 */
@Mapper
public interface TrxBigIntegerMapper extends BaseMapper<TrxBigInteger> {
    TrxBigInteger getById();
}
