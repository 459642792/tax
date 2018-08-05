package com.blueteam.wineshop.controller;

import com.blueteam.base.cache.redis.Redis;
import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.Common;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.mapper.ThirdPartyUserInfoMapper;
import com.blueteam.wineshop.service.*;
import com.blueteam.wineshop.service.wechatapplet.OrderCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/discoverC")
public class DiscoverController extends BaseController {
    private static final Logger logger= LoggerFactory.getLogger(DiscoverController.class);

    @Autowired
    DiscoverService discoverService;
    @Autowired
    ImageInfoService imageInfoService;
    @Autowired
    ContinuationService continuationService;
    @Autowired
    VendorInfoService vendorInfoService;
    @Autowired
    ScoreInfoService scoreInfoService;
    @Autowired
    SmallRoutineCommentService smallRoutineCommentService;
    @Autowired
    UpvoteRecordService upvoteRecordService;
    @Autowired
    UserService userInfoService;
    @Autowired
    ReVendorService revendorService;
    @Autowired
    private ThirdPartyUserInfoMapper thirdPartyUserInfoMapper;
    @Resource
    private OrderCommentService rderCommentServiceo;
    /**
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/discoverList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult discoverList(@RequestParam("Code") String Code, HttpServletResponse response) throws Exception {
        if (Code.isEmpty()) {
            return ApiResult.error("传入的参数不正确");
        }
        List<Discover> lstDiscover = discoverService.HeandLineList(Code, Continuation.Continuation_type);
        return ApiResult.success(lstDiscover);
    }

    @RequestMapping(value = "/disDetailList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult disDetailList(@RequestParam("Code") String Code, @RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex, HttpServletResponse response) throws Exception {
        if (Code.isEmpty()) {
            return ApiResult.error("传入的参数不正确");
        }
        List<Discover> lstDiscover = discoverService.DisCoverHeandList(Code, Continuation.Continuation_type, pageSize, pageIndex);
        int totalCount = discoverService.DisCoverHeandCount(Code, Continuation.Continuation_type);
        for (int i = 0; i < lstDiscover.size(); i++) {
            List<String> lstStr = new ArrayList<String>();
            List<ImageInfo> lstImg = imageInfoService.ImageInfoList(Discover.FACE_IMAGE.toString(), lstDiscover.get(i).getId());
            for (int j = 0; j < lstImg.size(); j++) {
                lstStr.add(lstImg.get(j).getImage());
            }
            lstDiscover.get(i).setLstFaceImage(lstStr);
            Date currTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfss = new SimpleDateFormat("yyyy");
            //            Calendar date = Calendar.getInstance();
//            int year = Integer.parseInt(String.valueOf(date.get(Calendar.YEAR)));
//            if (Integer.parseInt(lstDiscover.get(i).getUpdateDate().substring(0, 4)) - year < 0 ) {
            if (!sdfss.format(currTime).equals(lstDiscover.get(i).getUpdateDate().substring(0, 4))){
                lstDiscover.get(i).setUpdateDate(lstDiscover.get(i).getUpdateDate().substring(0, 10));
            }else if (daysBetween(sdf.parse(lstDiscover.get(i).getUpdateDate().substring(0, 10)), sdf.parse(sdf.format(currTime))) > 0) {
                lstDiscover.get(i).setUpdateDate(lstDiscover.get(i).getUpdateDate().substring(5, 10).toString());
            } else {
                long nd = 1000 * 24 * 60 * 60;
                long nh = 1000 * 60 * 60;
                long nm = 1000 * 60;
                DateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long diff = currTime.getTime() - sdfs.parse(lstDiscover.get(i).getUpdateDate()).getTime();
                long hour = diff % nd / nh;
                long min = diff % nd % nh / nm;
                //当天内一小时以上
                if ((int) hour > 0) {
                    lstDiscover.get(i).setUpdateDate((int) hour + "小时前");
                } else {
                    //一小时内一分钟以上
                    if ((int) min > 0) {
                        lstDiscover.get(i).setUpdateDate((int) min + "分钟前");
                    } else {
                        //说明是一分钟内
                        lstDiscover.get(i).setUpdateDate("刚刚");
                    }
                }

            }
        }
        return ApiResult.success(lstDiscover, totalCount);
    }

    /**
     * 发现C端详情接口
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/discoverDetail", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult discoverDetail(@RequestParam("Id") int Id, HttpServletResponse response) throws Exception {
        if (Id <= 0) {
            return ApiResult.error("传入的参数不正确");
        }
        Discover objcover = discoverService.GetDiscover(Id);
        if (null == objcover) {
            return ApiResult.error("没有该发布信息");
        }
        List<Continuation> lstContinuation = continuationService.ContinuationList(Id, Continuation.vendor_link_type);
        List<Vendordiscover> lstdiscover = new ArrayList<Vendordiscover>();
        for (int i = 0; i < lstContinuation.size(); i++) {
            Vendordiscover objvendordis = new Vendordiscover();
            VendorInfo objvendor = vendorInfoService.vendorDetail(Integer.parseInt(lstContinuation.get(i).ExpandText1.equals("null") ? "0" : lstContinuation.get(i).ExpandText1));
            if (null != objvendor) {
                objvendordis.setId(objvendor.getId());
                objvendordis.setImage(objvendor.getImage());
                objvendordis.setVendorName(objvendor.getName());
                objvendordis.setVendorAddr(objvendor.getAddr());
                double newScore = rderCommentServiceo.averageScore(objvendor.getId());
               /* List<ScoreInfo> objInfo = scoreInfoService.CommonInfoList(objvendor.getId(), Constants.COMMENTVENDOR_CODE_VENDOR);
                for (int j = 0; j < objInfo.size(); j++) {
                    newScore = add(newScore, objInfo.get(j).getScore());
                }*/

//                objvendordis.setOutScore(objInfo.size() == 0 ? BigDecimal.valueOf(0) : BigDecimal.valueOf(newScore / objInfo.size()));
                objvendordis.setOutScore( BigDecimal.valueOf(newScore));
                lstdiscover.add(objvendordis);
            }
        }
        objcover.setLstcoverVendor(lstdiscover);
        discoverService.updateDiscoverVisits(Id, objcover.getVisits() + 1);//浏览量
        return ApiResult.success(objcover);
    }

    /**
     * C端发现的评论列表页接口（没有登录时）
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/commentList2", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult commentList2(@RequestParam("ForeignKey") Integer ForeignKey,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam("pageIndex") Integer pageIndex,
                                   HttpServletResponse response) throws Exception {
        List<SmallRoutineComment> lstSmallComment = smallRoutineCommentService.CommentListC(ForeignKey, SmallRoutineComment.DISCOVER_COMMENT_TYPE, pageSize, pageIndex);
        for (int i = 0; i < lstSmallComment.size(); i++) {
            Date currTime = new Date();
            Calendar date = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int year = Integer.parseInt(String.valueOf(date.get(Calendar.YEAR)));
            if (Integer.parseInt(lstSmallComment.get(i).getUpdateDate().substring(0, 4)) - year > 1) {
                lstSmallComment.get(i).setUpdateDate(lstSmallComment.get(i).getUpdateDate().substring(0, 10));
            } else if (daysBetween(sdf.parse(lstSmallComment.get(i).getUpdateDate().substring(0, 10)), sdf.parse(sdf.format(currTime))) > 1) {
                lstSmallComment.get(i).setUpdateDate(lstSmallComment.get(i).getUpdateDate().substring(6, 10));
            } else {
                long nd = 1000 * 24 * 60 * 60;
                long nh = 1000 * 60 * 60;
                long nm = 1000 * 60;
                DateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long diff = currTime.getTime() - sdfs.parse(lstSmallComment.get(i).getUpdateDate()).getTime();
                long hour = diff % nd / nh;
                long min = diff % nd % nh / nm;
                //当天内一小时以上
                if ((int) hour > 0) {
                    lstSmallComment.get(i).setUpdateDate((int) hour + "小时前");
                } else {
                    //一小时内一分钟以上
                    if ((int) min > 0) {
                        lstSmallComment.get(i).setUpdateDate((int) min + "分钟前");
                    } else {
                        //说明是一分钟内
                        lstSmallComment.get(i).setUpdateDate("刚刚");
                    }
                }

            }
        }
        int totalCount = smallRoutineCommentService.CommentCount(ForeignKey, SmallRoutineComment.DISCOVER_COMMENT_TYPE);
        return ApiResult.success(lstSmallComment, totalCount);
    }

    /**
     * C端发现的评论列表页接口
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/commentList", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin
    public BaseResult commentList(@RequestParam("ForeignKey") Integer ForeignKey,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("pageIndex") Integer pageIndex,
                                  HttpServletResponse response) throws Exception {
        List<SmallRoutineComment> lstSmallComment = smallRoutineCommentService.CommentListC(ForeignKey, SmallRoutineComment.DISCOVER_COMMENT_TYPE, pageSize, pageIndex);
        for (int i = 0; i < lstSmallComment.size(); i++) {
            UpVoteRecord objRecord = upvoteRecordService.upvoteRecordDetail(lstSmallComment.get(i).getId(), this.getCurrentUserID());
            lstSmallComment.get(i).setStatus(objRecord == null ? "N" : "Y");
            //表示是当前用户发布的评论信息
            if (lstSmallComment.get(i).getUserId() == this.getCurrentUserID()) {
                lstSmallComment.get(i).setState("Y");
            } else {
                lstSmallComment.get(i).setState("N");
            }
            Date currTime = new Date();
            Calendar date = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int year = Integer.parseInt(String.valueOf(date.get(Calendar.YEAR)));
            if (Integer.parseInt(lstSmallComment.get(i).getUpdateDate().substring(0, 4)) - year > 1) {
                lstSmallComment.get(i).setUpdateDate(lstSmallComment.get(i).getUpdateDate().substring(0, 10));
            } else if (daysBetween(sdf.parse(lstSmallComment.get(i).getUpdateDate().substring(0, 10)), sdf.parse(sdf.format(currTime))) > 1) {
                lstSmallComment.get(i).setUpdateDate(lstSmallComment.get(i).getUpdateDate().substring(5, 10));
            } else {
                long nd = 1000 * 24 * 60 * 60;
                long nh = 1000 * 60 * 60;
                long nm = 1000 * 60;
                DateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long diff = currTime.getTime() - sdfs.parse(lstSmallComment.get(i).getUpdateDate()).getTime();
                long hour = diff % nd / nh;
                long min = diff % nd % nh / nm;
                //当天内一小时以上
                if ((int) hour > 0) {
                    lstSmallComment.get(i).setUpdateDate((int) hour + "小时前");
                } else {
                    //一小时内一分钟以上
                    if ((int) min > 0) {
                        lstSmallComment.get(i).setUpdateDate((int) min + "分钟前");
                    } else {
                        //说明是一分钟内
                        lstSmallComment.get(i).setUpdateDate("刚刚");
                    }
                }

            }
        }
        int totalCount = smallRoutineCommentService.CommentCount(ForeignKey, SmallRoutineComment.DISCOVER_COMMENT_TYPE);
        return ApiResult.success(lstSmallComment, totalCount);
    }

    /**
     * 删除评论信息
     */
    @RequestMapping(value = "/deleteComment", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult deleteComment(@RequestParam(value = "Id", required = false) Integer Id, HttpServletResponse response) throws Exception {
        if (Id == null || Id <= 0) {
            return ApiResult.error("传入参数不正确");
        }
        smallRoutineCommentService.deleteComment(Id);
        upvoteRecordService.deleteUpvote(Id);
        return ApiResult.success();
    }

    /**
     * 查询商家的营业执照等信息
     */
    @RequestMapping(value = "/vendorlicence", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult vendorlicence(@RequestParam(value = "Id", required = false) Integer Id, HttpServletResponse response) throws Exception {
        if (Id == null || Id <= 0) {
            return ApiResult.error("传入参数信息不正确");
        }
        VendorInfo objvendor = vendorInfoService.vendorDetail(Id);
        if (null == objvendor) {
            return ApiResult.error("没有查询到该商家信息");
        }
        return ApiResult.success(objvendor);
    }

    /**
     * C端发现评论信息(新增)
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/insertComment", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult insertComments(@RequestParam(value = "ForeignKey", required = false) Integer ForeignKey, @RequestParam(value = "Content", required = false) String Content, HttpServletResponse response) throws Exception {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = dateFormat.format(now);
        SmallRoutineComment objComment = new SmallRoutineComment();
        objComment.setUpVote(0);
        objComment.setUserId(this.getCurrentUserID());
        UserInfo objInfo = userInfoService.selectByPrimaryKey(this.getCurrentUserID());
        List<ThirdPartyUserInfo> lists = thirdPartyUserInfoMapper.listThirdPartyUserInfo(super.getCurrentUserID(), Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
        if (null != lists && lists.size() != 0) {
            objComment.setUserName(lists.get(0).getThirdPartyNickName() != null && !lists.get(0).getThirdPartyNickName().equals("") ? lists.get(0).getThirdPartyNickName() : objInfo.getTelephone());
            objComment.setUserImage(lists.get(0).getThirdPartyHeadImage());
        } else {
            objComment.setUserName(objInfo.getTelephone());
            objComment.setUserImage("");
        }
        objComment.setCreateDate(date);
        objComment.setCreateBy(this.getUserName());
        objComment.setUpdateDate(date);
        objComment.setUpdateBy(this.getUserName());
        objComment.setContent(Content);
        objComment.setType(SmallRoutineComment.DISCOVER_COMMENT_TYPE);
        objComment.setForeignKey(ForeignKey);
        smallRoutineCommentService.insertComment(objComment);
        return ApiResult.success();
    }

    /**
     * 点赞
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/insertUpvote", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin
    public BaseResult insertUpvote(@RequestParam(value = "Id", required = false) Integer Id, @RequestParam(value = "Status", required = false) String Status, HttpServletResponse response) throws Exception {
        if (Id == null || Id <= 0) {
            return ApiResult.error("参数传入错误");
        }
        SmallRoutineComment objComment = smallRoutineCommentService.CommentDetail(Id);
        if (null == objComment) {
            return ApiResult.error("没有查询到对应的数据信息");
        }
        ApiResult objResult = new ApiResult();
        if (Status.equals("Y")) {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String date = dateFormat.format(now);
            smallRoutineCommentService.updateUpVote(Id, objComment.getUpVote() + 1);
            UpVoteRecord objRecord = new UpVoteRecord();
            UserInfo objInfo = userInfoService.selectByPrimaryKey(this.getCurrentUserID());
            objRecord.setUserName(objInfo == null ? "" : objInfo.getUsername());
            objRecord.setForeignKey(Id);
            objRecord.setUserImage(objInfo.getHeadimage());
            objRecord.setUserId(this.getCurrentUserID());
            objRecord.setCreateDate(date);
            objRecord.setCreateBy(this.getUserName());
            objRecord.setUpdateDate(date);
            objRecord.setUpdateBy(this.getUserName());
            int count = upvoteRecordService.RecordCount(Id, this.getCurrentUserID());
            if (count > 0) {
                return ApiResult.error("不能重复点赞");
            } else {
                upvoteRecordService.insertUpvoteRecord(objRecord);
                objResult.setReturnId(String.valueOf(objRecord.getId()));
            }
        } else {
            upvoteRecordService.deleteUpvoteRecord(Id, this.getCurrentUserID());
            smallRoutineCommentService.updateUpVote(Id, objComment.getUpVote() - 1);
        }
        objResult.setSuccess(true);
        objResult.success(true);
        objResult.setMessage("点赞成功");
        objResult.setStatus("200");
        return objResult;
    }

    /**
     * 推荐消息
     *
     * @param Id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getMessageRecipient", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin
    public BaseResult getMessageRecipient(@RequestParam(value = "Id") Integer Id, HttpServletResponse response) throws Exception {
        if (null == Id) return ApiResult.error("参数参数信息不正确");
        MessageRecipient objRe = new MessageRecipient();
        UpVoteRecord objRecord = upvoteRecordService.getRecord(Id);
        if (null == objRecord) return ApiResult.error("不存在该点赞记录");
        objRe.setUserId(objRecord.getUserId());
        objRe.setCarriersId(0);
        objRe.setVendorId(0);
        return ApiResult.success(objRe);
    }

    /**
     * C端首页推荐发现接口信息
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ReDiscover", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult ReDiscover(HttpServletResponse response) throws Exception {
        List<Discover> lstDis = discoverService.lstGroom();
        for (int i = 0; i < lstDis.size(); i++) {
            List<String> lstImages = new ArrayList<String>();
            List<ImageInfo> lstImage = imageInfoService.ImageInfoList(Discover.FACE_IMAGE, lstDis.get(i).getId());
            if (lstImage.size() > 0) lstImages.add(lstImage.get(0).getImage());
            lstDis.get(i).setLstFaceImage(lstImages);
        }
        return ApiResult.success(lstDis, lstDis.size());
    }

    /**
     * 修改点击量
     *
     * @param Id
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upClickCount", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult upClickCount(@RequestParam(value = "Id", required = false) Integer Id){
        if (null == Id || Id <= 0) return ApiResult.error("传入参数不正确");
//        ReVendor objvendor = revendorService.revendorDetail(Id);
//        if (null == objvendor) return ApiResult.error("推荐商家不存在");
//        objvendor.setClickCount(objvendor.getClickCount() + 1);
//        revendorService.updateClick(objvendor);
        VendorInfo info=vendorInfoService.getNewVendorById(Id);
        if (null == info) return ApiResult.error("商家不存在");
        vendorInfoService.increaseOrderStatistics(info.getId(),null,1,null);

        String key= Common.getRedisKeyOfPageView(new Date(),Id);
        JedisCommands jedis=Redis.getJedis();
        if (jedis.exists(key)){
            jedis.incr(key);
        }else {
            jedis.set(key,"1");
            jedis.expire(key,25*60*60);
        }
        return ApiResult.success();
    }

    /**
     * 判断相差几天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }


    /**
     * 两个Double记性计算
     */
    public double add(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.add(b2).doubleValue();
    }

}
