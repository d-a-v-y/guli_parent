package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //生成订单的方法
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用获取用户信息（根据用户id）
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

        //通过远程调用获取课程信息（根据课程id）
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建Order对象,向order对象里面设置需要的数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());  //订单号
        order.setCourseId(courseId);  //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());  //课程标题
        order.setCourseCover(courseInfoOrder.getCover());  //课程封面
        order.setTeacherName(courseInfoOrder.getTeacherName());  //讲师名称
        order.setTotalFee(courseInfoOrder.getPrice());  //课程价格
        order.setMemberId(memberId);  //用户id
        order.setMobile(userInfoOrder.getMobile());  //用户手机
        order.setNickname(userInfoOrder.getNickname());  //用户昵称
        order.setStatus(0);   //支付状态  订单状态（0：未支付  1：已支付）
        order.setPayType(1);  //支付类型，'1'代表微信支付

        baseMapper.insert(order);

        //返回订单号
        return order.getOrderNo();
    }
}
