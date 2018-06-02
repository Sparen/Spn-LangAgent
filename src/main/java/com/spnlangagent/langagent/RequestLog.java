package com.spnlangagent.langagent;

import com.google.gson.Gson;

/**
 * Request Log object that stores a request URL and various metrics for the specific request.
 */
public class RequestLog {

    /**
     * Request URL for this request.
     */
    private String requestURL;

    /**
     * Request Time for this request (ms).
     */
    private double requestTime;

    /**
     * Number of strings created for this request.
     */
    private int stringsCreated;

    /**
     * Amount of memory allocated for this request.
     */
    private int memoryAllocated;

    /**
     * Default constructor for the RequestLog class.
     */
    public RequestLog() {
        this.requestURL = "";
        this.requestTime = 0.0;
        this.stringsCreated = 0;
        this.memoryAllocated = 0;
    }

    /**
     * Standard constructor for the RequestLog class.
     * @param requestURL the request URL for this request log.
     * @param requestTime the request time for this request log.
     * @param stringsCreated the number of strings created for this request log.
     * @param memoryAllocated the amount of allocated memory for this request log.
     */
    public RequestLog(String requestURL, double requestTime, int stringsCreated, int memoryAllocated) {
        this.requestURL = requestURL;
        this.requestTime = requestTime;
        this.stringsCreated = stringsCreated;
        this.memoryAllocated = memoryAllocated;
    }

    /**
     * Gets the request URL for this request log.
     * @return request url for this request log.
     */
    public String getRequestURL() {
        return this.requestURL;
    }

    /**
     * Gets the request time for this request log.
     * @return request time for this request log.
     */
    public double getRequestTime() {
        return this.requestTime;
    }

    /**
     * Gets the number of strings created for this request log.
     * @return number of strings created for this request log.
     */
    public int getStringsCreated() {
        return this.stringsCreated;
    }

    /**
     * Gets the amount of memory allocated for this request log.
     * @return amount of memory allocated for this request log.
     */
    public int getMemoryAllocated() {
        return this.memoryAllocated;
    }

    /**
     * Sets the request URL from this request log.
     * @param requestURL the request URL for this request log.
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    /**
     * Sets the request time from this request log.
     * @param requestTime the request time for this request log.
     */
    public void setRequestTime(double requestTime) {
        this.requestTime = requestTime;
    }

    /**
     * Sets the number of strings created for this request log.
     * @param stringsCreated the number of strings created for this request log.
     */
    public void setStringsCreated(int stringsCreated) {
        this.stringsCreated = stringsCreated;
    }

    /**
     * Sets the amount of memory allocated for this request log.
     * @param memoryAllocated the amount of memory allocated for this request log.
     */
    public void setMemoryAllocated(int memoryAllocated) {
        this.memoryAllocated = memoryAllocated;
    }

    /**
     * Returns JSON representation of this object via GSON library.
     * @return JSON representation of this object.
     */
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
