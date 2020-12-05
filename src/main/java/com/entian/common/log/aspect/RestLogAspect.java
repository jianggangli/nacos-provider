package com.entian.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.entian.common.log.annotation.RestLog;
import com.entian.common.log.model.RestLogModel;
import com.entian.common.log.service.AsyncLogService;
import com.entian.common.log.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志记录处理
 *
 * @author jianggangli
 */
@Slf4j
@Aspect
@Component
public class RestLogAspect
{
    @Autowired
    private Environment env;

    @Autowired
    private AsyncLogService asyncLogService;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping(){
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping(){
    }

    @Pointcut("@annotation(com.entian.common.log.annotation.RestLog)")
    public void logPointCut(){
    }

    @Before(value = "logPointCut()")
    public void doBefore() {
//        System.out.println("doBefore");
    }

    @Around(value = "getMapping() || postMapping() || requestMapping()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Long startTs = System.currentTimeMillis();
        Long endTs = 0L;
        /*前置通知方法*/
        /*执行目标方法*/
        try {
            result = joinPoint.proceed();

            /*返回通知方法*/
        } catch (Throwable e) {
            endTs = System.currentTimeMillis();
            handleLog(joinPoint, e, null, startTs, endTs);
            /*异常通知方法*/
            /*环绕通知方法本身还有其它异常时*/
            throw e;
        }
        endTs = System.currentTimeMillis();
        handleLog(joinPoint, null, result, startTs, endTs);
        /*后置通知*/

        return result;
    }

    @After(value = "logPointCut()")
    public void doAfter() {
//        System.out.println("doAfter");
    }


    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result)
    {
//        System.out.println("doAfterReturning");
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e)
    {
//        System.out.println("doAfterThrowing");
    }

    protected void handleLog(final JoinPoint joinPoint, final Throwable e, Object jsonResult, Long startTs,
            Long endTs) {

        try {
            // 获得注解
            RestLog restLogAnnotation = getAnnotationLog(joinPoint);
            if (restLogAnnotation == null) {
//                return;
            }
            // *========数据库日志=========*//
            RestLogModel restLogModel = new RestLogModel();
            /**
             * 服务名
             */
            String service = env.getProperty("spring.application.name");
            restLogModel.setService(service);
            /**
             * 模块名
             */
            restLogModel.setModule("默认");
            if (restLogAnnotation != null) {
                String module = restLogAnnotation.module();
                restLogModel.setModule(module);
            }
            /**
             * 客户端ip
             */
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert attributes != null;
            HttpServletRequest request = attributes.getRequest();
            String clientIp = IpUtil.getIpAddr(request);
            restLogModel.setClientIp(clientIp);
            /**
             * 请求映射路径
             */
            String requestUri = request.getRequestURI();
            restLogModel.setRequestUri(requestUri);
            /**
             * 请求方式
             */
            String requestMethod = request.getMethod();
            restLogModel.setRequestMethod(requestMethod);
            /**
             * 参数值
             */
            List<String> paramValues = getParameterValueList(joinPoint.getArgs());

//            if (restLogAnnotation.isSaveRequestData()) {
                // 获取参数的信息，传入到数据库中。
                restLogModel.setParamValue(paramValues);
//            }
            /**
             * 参数名称
             */
            List<String> paramNames = getParameterNameList(joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName());
            restLogModel.setParamName(paramNames);
            /**
             * 参数类型
             */
            List<String> paramTypes = getParameterTypeList(joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName());
            restLogModel.setParamType(paramTypes);
            /**
             * 方法名
             */
            String className = joinPoint.getTarget().getClass().getName();
            String method = joinPoint.getSignature().getName();
            restLogModel.setMethod(className + "." + method + "()");
            /**
             * 操作人姓名
             */
            String user = "jianggangli";
            restLogModel.setUser(user);
            /**
             * 接口返回数据
             */
            String returnData = JSON.toJSONString(jsonResult);
            restLogModel.setBaseResult(returnData);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
            /**
             * 接口请求时间
             */
            restLogModel.setStartTime(simpleDateFormat.format(startTs));
            /**
             * 接口返回时间
             */
            restLogModel.setEndTime(simpleDateFormat.format(endTs));
            /**
             * 总消耗时间
             */
            int spendTime = (int) (endTs - startTs);
            restLogModel.setSpendTime(spendTime);

            if (e != null) {
                restLogModel.setStatus("error");
                restLogModel.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
                restLogModel.setErrorType(StringUtils.substring(e.getClass().getTypeName(), 0, 2000));
            } else {
                restLogModel.setStatus("success");
                restLogModel.setErrorMsg("");
            }

            // 保存数据库
            asyncLogService.saveLogModel(restLogModel);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    private List<String> getParameterNameList(Class clazz, String methodName) {
        List<String> parameterList = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                //直接通过method就能拿到所有的参数
                Parameter[] params = method.getParameters();
                for (Parameter parameter : params) {
                    parameterList.add(parameter.getName());
                }
            }
        }
        return parameterList;
    }

    private List<String>  getParameterTypeList(Class clazz, String methodName){
        List<String> parameterTypeList = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                //直接通过method就能拿到所有的参数类型
                Class<?>[] types = method.getParameterTypes();
                for (Class<?> type  : types) {
                    parameterTypeList.add(type.getName());
                }
            }
        }
        return parameterTypeList;
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private RestLog getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(RestLog.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private List<String> getParameterValueList(Object[] paramsArray)
    {
        List<String> params = new ArrayList<>();
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (Object o : paramsArray) {
                if (o == null) {
                    params.add("[null]");
                }else if (!isFilterObject(o)) {
                    Object jsonObj = JSON.toJSON(o);
                    params.add(jsonObj.toString());
                } else {
                    params.add("[" + o.getClass().getName() + "]");
                }
            }
        }
        return params;
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o)
    {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse ;
    }
}
