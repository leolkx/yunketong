package com.example.YunKeTong.http;

import com.example.YunKeTong.httpBean.CourseInfoBean;
import com.example.YunKeTong.httpBean.CoursesListBean;
import com.example.YunKeTong.httpBean.DefaultResultBean;
import com.example.YunKeTong.httpBean.DictInfoListBean;
import com.example.YunKeTong.httpBean.LoginBean;
import com.example.YunKeTong.httpBean.RegisterBean;
import com.example.YunKeTong.httpBean.SearchListBean;
import com.example.YunKeTong.httpBean.StudentsListBean;
import com.example.YunKeTong.httpBean.SystemBean;
import com.example.YunKeTong.httpBean.UploadAvatarBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitInterface {


    /**
     * 注册接口
     *
     * @param params
     *           email : 邮箱
     *           email_code : 验证码
     *           phone : 手机号
     *           password : 密码
     *           type : 用户类型，2是老师，3是学生
     *           name : 姓名
     *           stu_code : 学工号
     *           school : 学校
     *           department : 院系
     *           profession : 专业
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     */
    @Multipart
    @POST("user/register")
    Observable<RegisterBean> httpRegisterInterface(@PartMap Map<String, String> params);

    /**
     * 登录接口
     * @param params
     *           username : 用户名/邮箱/手机号
     *           password : 密码
     * @return
     *          result_code : http状态码
     *          result_desc : 状态码描述
     *          token
     *          uid : 用户id号
     *          email : 邮箱
     *          nick_name : 昵称
     *          type : 用户类型
     *          avatar : 用户头像
     *          userInfo : 用户信息
     */
    @Multipart
    @POST("user/login")
    Observable<LoginBean> httpLoginInterface(@PartMap Map<String, String> params);

    /**
     * 忘记密码接口
     *
     * @param params
     *          email : 邮箱
     *          email_code : 验证码
     *          new_pwd : 新密码
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     */
    @Multipart
    @PUT("user/password")
    Observable<DefaultResultBean<Object>> httpForgotPwdInterface(@PartMap Map<String, String> params);

    /**
     * 修改用户信息接口
     *
     * @param params
     *           token
     *           uid : 用户id号
     *           phone : 手机号
     *           nick_name : 昵称
     *           gender : 性别
     *           stu_code : 学工号
     *           school : 学校
     *           department : 院系
     *           profession : 专业
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     */
    @Multipart
    @PUT("user/info")
    Observable<DefaultResultBean<Object>> httpModifyUserInfoInterface(@PartMap Map<String, String> params);

    /**
     * 查看系统信息
     *
     * @param params
     *           token
     *           infoid ： 恒为1
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     *           id : ?
     *           experience : ？
     */
    @Multipart
    @POST("system/infos")
    Observable<SystemBean> httpGetSystemInfo(@PartMap Map<String, String> params);

    /**
     * 发送邮箱验证码
     * @param email
     *           email : 邮箱
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     */
    @Multipart
    @POST("user/email_code")
    Observable<DefaultResultBean<Object>> httpSendEmailInterface(@Part("email") String email);

    /**
     * 上传头像（先不做）
     *
     * @param params
     * @param file
     * @return
     */
    @Multipart
    @POST("user/avatar")
    Observable<UploadAvatarBean> httpUploadAvatarInterface(@PartMap Map<String, String> params, @Part MultipartBody.Part file);

    /**
     * 学生添加课程到课表
     *
     * @param params
     *           token
     *           uid : 用户id
     *           course_id : 课程id
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     */
    @Multipart
    @POST("course/add")
    Observable<DefaultResultBean<Object>> httpAddCourseInterface(@PartMap Map<String, String> params);

    /**
     * 获取课程学生列表
     *
     * @param token
     *           token
     *           course_id : 课程id
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     *           id : ？
     *           name : 姓名
     *           check_count : 签到次数
     */
    @GET("course/students")
    Observable<StudentsListBean> httpGetStudentsListInterface(@Query("token") String token, @Query("course_id") String course_id);


    /**
     * 搜索课程（先不做）
     *
     * @return
     */
    @GET("course/search")
    Observable<SearchListBean> httpSearchCourseInterface(@QueryMap Map<String, String> params);

    /**
     * 获取课程信息
     *
     * @param token
     * @param course_id
     *           token
     *           course_id : 课程id
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     *           course_name : 课程名称
     *           course_code : 课程代码
     *           place : 上课地点
     *           time ： 上课时间
     *           stu_count ： 学生人数
     *           teacher ： 教师姓名
     *           creater_uid ： ？
     *           check_count ： 点名次数
     */
    @GET("course/info")
    Observable<CourseInfoBean> httpGetCourseInfoInterface(@Query("token") String token, @Query("course_id") String course_id);

    /**
     * 修改课程信息（先不做）
     *
     * @param params
     * @return
     */
    @Multipart
    @PUT("course/info")
    Observable<DefaultResultBean<Object>> httpModifyCourseInfoInterface(@PartMap Map<String, String> params);

    /**
     * 从课程删除学生（先不做）
     *
     * @param params
     * @return
     */
    @Multipart
    @DELETE("course/students")
    Observable<DefaultResultBean<Object>> httpDeleteStudentInterface(@PartMap Map<String, String> params);

    /**
     * 删除签到信息（先不做）
     *
     * @param params
     * @return
     */
    @Multipart
    @DELETE("course/checklist")
    Observable<DefaultResultBean<Object>> httpDeleteCheckInterface(@PartMap Map<String, String> params);

    /**
     * 修改学生信息（先不做）
     *
     * @param params
     * @return
     */
    @Multipart
    @PUT("course/students")
    Observable<DefaultResultBean<Object>> httpModifyStudentInterface(@PartMap Map<String, String> params);

    /**
     * 修改签到信息（先不做）
     *
     * @param params
     * @return
     */
    @Multipart
    @PUT("course/checklist")
    Observable<DefaultResultBean<Object>> httpModifyCheckInterface(@PartMap Map<String, String> params);

    /**
     * 添加学生到课程（先不做）
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("course/stu2course")
    Observable<DefaultResultBean<Object>> httpAddStu2CourseInterface(@PartMap Map<String, String> params);

    /**
     * 获取课程列表
     *
     * @param params
     *           token
     *           uid : 用户id
     *           type : 用户类型
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     *           course_id ： 课程id
     *           course_name : 课程名称
     *           teacher : 教师姓名
     *           time : 上课时间
     */
    @GET("course/course")
    Observable<CoursesListBean> httpGetCoursesListInterface(@QueryMap Map<String, String> params);

    /**
     * 创建课程
     *
     * @param params
     *           token
     *           uid ： 用户id
     *           course_name : 课程名称
     *           place ： 上课地点
     *           teacher : 教师姓名
     *           time : 上课时间
     *           stu_count ： 学生人数
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     */
    @Multipart
    @POST("course/create")
    Observable<DefaultResultBean<Object>> httpCreateCourseInterface(@PartMap Map<String, String> params);

    /**
     * 签到
     *
     * @param params
     *           token
     *           uid ： 用户id
     *           course_id : 课程id
     *           time : 签到时间
     *           location ： 签到地点
//     * @param file
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     */
    @Multipart
    @POST("check/check")
//    Observable<DefaultResultBean<Boolean>> httpCheckInterface(@PartMap Map<String, String> params, @Part MultipartBody.Part file);
    Observable<DefaultResultBean<Boolean>> httpCheckInterface(@PartMap Map<String, String> params);

    /**
     * 开始签到
     *
     * @param params
     *           token
     *           course_id : 课程id
     *           start_time : 签到开始时间
     *           duration ： 签到持续时间
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     */
    @Multipart
    @POST("check/startCheck")
    Observable<DefaultResultBean<Object>> httpStartCheckInterface(@PartMap Map<String, String> params);

    /**
     * 停止签到
     *
     * @param params
     *           token
     *           course_id : 课程id
     *           end_time : 签到结束时间
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     */
    @Multipart
    @POST("check/stop")
    Observable<DefaultResultBean<Object>> httpStopCheckInterface(@PartMap Map<String, String> params);

    /**
     * 查询是否能签到
     *
     * @param params
     *           token
     *           course_id : 课程id
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     */
    @Multipart
    @POST("check/cancheck")
    Observable<DefaultResultBean<Boolean>> httpCanCheckInterface(@PartMap Map<String, String> params);

    /**
     * 获取字典内容
     *
     * @param token
     * @param typename
     *           token
     *           typename : ？
     * @return
     *           result_code : http状态码
     *           result_desc : 状态码描述
     *           id ： ？
     *           type_level : ?
     *           type_belong ： ？
     *           info ： ？
     */
    @GET("dict/infos4name")
    Observable<DictInfoListBean> httpGetDictInfoInterface(@Query("token") String token, @Query("typename") String typename);

}
