package com.journaldev.servlet.async;

import java.io.IOException;


/*
 * 
 * After sending below request
 * http://localhost:8090/AsyncServletExample/AsyncLongRunningServlet?time=10
 * 
 * Output:  
AsyncLongRunningServlet Start::Name=http-bio-8090-exec-10::ID=33
AsyncLongRunningServlet End::Name=http-bio-8090-exec-10::ID=33::Time Taken=1 ms.
Async Supported? true
AppAsyncListener onComplete
 * 
 * Here, After getting the request, current thread release after 1 ms and handled over long task to Thread pool.
 * That will execute the task. On completion task onComplete method will be called. 
 */
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/AsyncLongRunningServlet", asyncSupported = true)
public class AsyncLongRunningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		System.out.println("AsyncLongRunningServlet Start::Name="
				+ Thread.currentThread().getName() + "::ID="
				+ Thread.currentThread().getId());

		request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);

		String time = request.getParameter("time");
		int secs = Integer.valueOf(time);
		// max 10 seconds
		if (secs > 10000)
			secs = 10000;

		AsyncContext asyncCtx = request.startAsync();
		asyncCtx.addListener(new AppAsyncListener());
		asyncCtx.setTimeout(9000);

		ThreadPoolExecutor executor = (ThreadPoolExecutor) request
				.getServletContext().getAttribute("executor");

		executor.execute(new AsyncRequestProcessor(asyncCtx, secs));
		long endTime = System.currentTimeMillis();
		System.out.println("AsyncLongRunningServlet End::Name="
				+ Thread.currentThread().getName() + "::ID="
				+ Thread.currentThread().getId() + "::Time Taken="
				+ (endTime - startTime) + " ms.");
	}

}
