package com.blueteam.wineshop.mapper;

import com.blueteam.entity.bo.AdminTradeBO;
import com.blueteam.entity.dto.TradeListSearchDTO;
import com.blueteam.entity.vo.AdminTradeListVO;

import java.util.List;

/**
 * Created by  NastyNas on 2018/1/15.
 */
public interface TradeAdminMapper {
    Integer countTradeInfo(TradeListSearchDTO tradeListSearchDTO);

    List<AdminTradeListVO> listTradeInfo(TradeListSearchDTO tradeListSearchDTO);

    List<AdminTradeBO> getTradeInfo(TradeListSearchDTO tradeListSearchDTO);

    List<AdminTradeBO> getTradeListInfo(TradeListSearchDTO tradeListSearchDTO);
}
