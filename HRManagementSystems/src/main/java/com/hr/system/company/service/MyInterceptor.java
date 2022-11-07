/*
 * package com.hr.system.company.service;
 * 
 * import java.util.Date; import java.util.List;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Component; import
 * org.springframework.web.context.request.WebRequestInterceptor; import
 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter; import
 * org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;
 * 
 * import com.hr.system.company.bean.AnnouncementAccessBean; import
 * com.hr.system.company.repository.AnnouncementRepository;
 * 
 * @Component public class MyInterceptor extends HandlerInterceptorAdapter {
 * 
 * 
 * 
 * @Autowired private AnnouncementRepository announcementRepo;
 * 
 * 
 * 
 * @Override public boolean preHandle(HttpServletRequest request,
 * HttpServletResponse response, Object handler) throws Exception {
 * 
 * 
 * return super.preHandle(request, response, handler); }
 * 
 * 
 * @Override public boolean preHandle(HttpServletRequest request,
 * HttpServletResponse response, Object handler) throws Exception {
 * request.setAttribute("myFirstAttribute", "MyFirstValueHere"); return
 * super.preHandle(request, response, handler); }
 * 
 * 
 * }
 * 
 */