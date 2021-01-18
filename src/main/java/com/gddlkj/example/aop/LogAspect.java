package com.gddlkj.example.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gddlkj.example.annotation.OperationLog;
import com.gddlkj.example.mapper.UserLogMapper;
import com.gddlkj.example.model.constants.OperationType;
import com.gddlkj.example.model.domain.UserLog;
import com.gddlkj.example.model.dto.R;
import com.gddlkj.example.util.IPUtil;
import com.gddlkj.example.util.SecurityUtil;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author blank
 * 注解方式记录日志
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Resource
    private UserLogMapper userLogMapper;

    @Resource
    private SecurityUtil securityUtil;

    @Pointcut("@annotation(com.gddlkj.example.annotation.OperationLog)")
    public void pointCut() {
    }


    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res = null;
        long time = System.currentTimeMillis();
        try {
            res = joinPoint.proceed();
            return res;
        } finally {
            try {
                //方法执行完成后增加日志
                time = System.currentTimeMillis() - time;
                addOperationLog(joinPoint, res, time);
            } catch (Exception e) {
                log.error("LogAspect 操作失败：" + e.getMessage());
            }
        }
    }

    private void addOperationLog(JoinPoint joinPoint, Object res, long time) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLog annotation = signature.getMethod().getAnnotation(OperationLog.class);
        UserLog operationLog = new UserLog();

        Map<Object, Object> map = new HashMap<>(4);
        Object[] args = joinPoint.getArgs();
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < argNames.length; i++) {
            map.put(argNames[i], args[i]);
        }
        String argsText = new ObjectMapper().writeValueAsString(map);

        if (annotation != null) {
            // 登陆日志特殊处理
            if (annotation.operationType().equals(OperationType.LOGIN)) {
                if (res instanceof R) {
                    R res1 = (R) res;
                    String[] result = res1.getData().toString().split("\\|");
                    operationLog.setUserId(Long.valueOf(result[1]));
                    res1.setData(result[0]);
                } else
                    return;
            }
            operationLog.setLogDesc(getDetail(argsText, annotation));
            operationLog.setLogType(annotation.operationType().getValue());
        }
        // 运行时间
        operationLog.setRunTime(time);
        // ip地址
        operationLog.setLoginIp(IPUtil.getIpAddr());
        if (securityUtil.getAuthentication() != null)
            operationLog.setUserId(securityUtil.getAuthentication().getId());
        operationLog.setArgs(argsText);
        operationLog.setMethodName(signature.getDeclaringTypeName() + "." + signature.getName());
        userLogMapper.insert(operationLog);
    }

    /**
     * 对当前登录用户和占位符处理
     *
     * @param argsText   方法参数
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    private String getDetail(String argsText, OperationLog annotation) {
        String detail = annotation.detail();
        try {
            Pattern pat = Pattern.compile("\\{\\{(.*?)}}");
            Matcher mat = pat.matcher(detail);
            while (mat.find()) {
                String k = mat.group(1);
                Object v = JsonPath.read(argsText, "$." + k).toString();
                if (v != null)
                    detail = detail.replace("{{" + k + "}}", v.toString());
            }
        } catch (Exception e) {
            log.error("占位符替换失败：" + e.getMessage());
        }
        return detail;
    }
}
