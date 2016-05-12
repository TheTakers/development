package com.sophia.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintStream;
import java.util.Stack;

/**
 * Created by Kim on 2015/9/23.
 */
public class DiagnosticInterceptor implements HandlerInterceptor {

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        Diagnostic.endCall();
        Diagnostic.finishDiagnostic().printStackTrace();
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        Diagnostic.endCall();
//        Diagnostic.startCall("start renderer " + (modelAndView == null ? "" : modelAndView.getViewName()));
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Diagnostic.startDiagnostic();
        Diagnostic.startCall("start call handler " + handler);
        return true;
    }

}

class CallTrace {

    private long startMillseconds;
    private long endMillseconds;
    private String callName;

    private Stack<CallTrace> callStack = new Stack<>();

    public void startCall(String callName) {
        this.callName = callName;
        this.startMillseconds = System.currentTimeMillis();
    }

    public void endCall() {
        this.endMillseconds = System.currentTimeMillis();
    }

    public CallTrace startSubCall(String callName) {
        CallTrace subCall = new CallTrace();
        subCall.startCall(callName);
        callStack.push(subCall);
        return subCall;
    }

    public Stack<CallTrace> getCallStack() {
        return callStack;
    }

    public long getStartMillseconds() {
        return startMillseconds;
    }

    public long getEndMillseconds() {
        return endMillseconds;
    }

    public String getCallName() {
        return callName;
    }

}

class Diagnostic {

    private static ThreadLocal<Diagnostic> diagnosticLocal = new ThreadLocal<>();
    private Stack<CallTrace> callStack = new Stack<>();
    private CallTrace rootCall;

    public Diagnostic() {

    }

    public void startDiagnostic0() {
        rootCall = new CallTrace();
        rootCall.startCall("root");
        callStack.push(rootCall);
    }

    public void finishDiagnostic0() {
        if (!callStack.isEmpty())
            callStack.pop().endCall();
    }

    public void startCall0(String callName) {
        callStack.push(callStack.peek().startSubCall(callName));
    }

    public void endCall0() {
        callStack.pop().endCall();
    }

    public static void startDiagnostic() {
        if (diagnosticLocal.get() == null) {
            diagnosticLocal.set(new Diagnostic());
        }
        diagnosticLocal.get().startDiagnostic0();
    }

    public static Diagnostic finishDiagnostic() {
        Diagnostic d = diagnosticLocal.get();
        d.finishDiagnostic0();
        if (diagnosticLocal.get() != null) {
            diagnosticLocal.remove();
        }
        return d;
    }

    public static void startCall(String callName) {

        diagnosticLocal.get().startCall0(callName);
    }

    public static void endCall() {
        diagnosticLocal.get().endCall0();

    }

    public static Diagnostic currentDiagnostic() {
        return diagnosticLocal.get();
    }

    public void printStackTrace() {
        printStackTrace(System.out);
    }

    public void printStackTrace(PrintStream w) {
        printCallStack(System.out, "", rootCall);
    }

    protected void printCallStack(PrintStream w, String delim, CallTrace ct) {
        w.println(delim + "Call[" + ct.getCallName() + "] started at " + ct.getStartMillseconds());
        for (CallTrace subCall : ct.getCallStack()) {
            printCallStack(w, delim + "  ", subCall);
        }
        w.println(delim + "Call[" + ct.getCallName() + "] finished in "
                + (ct.getEndMillseconds() - ct.getStartMillseconds()) + "ms.");
    }

}
