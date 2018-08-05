package com.blueteam.base.constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by libra on 2017/5/23.
 * <p>
 * 消息类型分组
 * TODO:到时候放在数据库，现在临时使用一下
 */
public class MessageTypeCodeGroups {
    /**
     * 通知分组
     */
    private static List<String> NOTICE_GROUP = null;

    /**
     * 评论分组
     */
    private static List<String> COMMENT_GROUP = null;

    /**
     * 商家交易分组
     */
    private static List<String> VENDOR_TRANSACTION_GROUP = null;

    /**
     * 商家结算分组
     */
    private static List<String> VENDOR_SETTLEMENT_GROUP = null;

    /**
     * 商家评价互动分组
     */
    private static List<String> VENDOR_COMMENT_GROUP = null;

    /**
     * 商家系统分组
     */
    private static List<String> VENDOR_SYSTEM_GROUP = null;


    /**
     * 消息分组
     */
    public static final HashMap<String, List<String>> MESSAGE_GROUPS = new HashMap<>();

    /**
     * C端通知分组
     */
    public static final String NOTICE = "NOTICE";

    /**
     * C端评价分组
     */
    public static final String COMMENT = "COMMENT";

    /**
     * 商家交易分组
     */
    public static final String VENDOR_TRANSACTION = "VENDOR_TRANSACTION";

    /**
     * 商家结算分组
     */
    public static final String VENDOR_SETTLEMENT = "VENDOR_SETTLEMENT";

    /**
     * 商家评价互动
     */
    public static final String VENDOR_COMMENT = "VENDOR_COMMENT";

    /**
     * 商家系统分组
     */
    public static final String VENDOR_SYSTEM = "VENDOR_SYSTEM";

    static {
        //C端通知分组
        List<String> list = new ArrayList<>();
        list.add(MessageTypeCodes.TRANSACTION);
        list.add(MessageTypeCodes.COUPON_EXPIRES);
        NOTICE_GROUP = Collections.unmodifiableList(list);

        //C端评论分组
        list = new ArrayList<>();
        list.add(MessageTypeCodes.COMMENT_EVALUATE_ORDER);
        list.add(MessageTypeCodes.DISCOVERED_COMMENT_LIKE);
        COMMENT_GROUP = Collections.unmodifiableList(list);

        //商家交易分组
        list = new ArrayList<>();
        list.add(MessageTypeCodes.TRANSACTION);
        VENDOR_TRANSACTION_GROUP = Collections.unmodifiableList(list);

        //商家结算分组
        list = new ArrayList<>();
        list.add(MessageTypeCodes.SETTLEMENT);
        VENDOR_SETTLEMENT_GROUP = Collections.unmodifiableList(list);

        //商家评价互动分组
        list = new ArrayList<>();
        list.add(MessageTypeCodes.EVALUATE_ORDER);
        VENDOR_COMMENT_GROUP = Collections.unmodifiableList(list);

        //商家系统消息分组
        list = new ArrayList<>();
        list.add(MessageTypeCodes.WINE_REDUCE);
        list.add(MessageTypeCodes.WINE_EXCHANGE_SEND);
        list.add(MessageTypeCodes.AUDIT_VENDOR_AUTHENTICATE);
        list.add(MessageTypeCodes.WINE_EXCHANGE);
        VENDOR_SYSTEM_GROUP = Collections.unmodifiableList(list);


        MESSAGE_GROUPS.put("NOTICE", NOTICE_GROUP);
        MESSAGE_GROUPS.put("COMMENT", COMMENT_GROUP);
        MESSAGE_GROUPS.put("VENDOR_TRANSACTION", VENDOR_TRANSACTION_GROUP);
        MESSAGE_GROUPS.put("VENDOR_SETTLEMENT", VENDOR_SETTLEMENT_GROUP);
        MESSAGE_GROUPS.put("VENDOR_COMMENT", VENDOR_COMMENT_GROUP);
        MESSAGE_GROUPS.put("VENDOR_SYSTEM", VENDOR_SYSTEM_GROUP);
    }


    /**
     * 获取商家交易消息分组
     *
     * @return
     */
    public static List<String> getVendorTransactionGroup() {
        return VENDOR_TRANSACTION_GROUP;
    }

    /**
     * 获取商家结算分组
     *
     * @return
     */
    private static List<String> getVendorSettlementGroup() {
        return VENDOR_SETTLEMENT_GROUP;
    }

    /**
     * 获取商家评价互动分组
     *
     * @return
     */
    private static List<String> getVendorCommentGroup() {
        return VENDOR_COMMENT_GROUP;
    }

    /**
     * 获取商家系统消息分组
     *
     * @return
     */
    private static List<String> getVendorSystemGroup() {
        return VENDOR_SYSTEM_GROUP;
    }


    /**
     * 获取C端通知类分组
     *
     * @return
     */
    public static List<String> getNoticeGroup() {
        return NOTICE_GROUP;
    }

    /**
     * 获取C端通知类分组
     *
     * @return
     */
    public static List<String> getCommentGroup() {
        return COMMENT_GROUP;
    }

}
