package com.spnlangagent.langagent;

import com.google.gson.Gson;

/**
 * Request Log object that stores a request URL and various metrics for the specific request.
 * Similar to insight's request/RequestAggregator.
 */
public class RequestLog {

    /**
     * Static Request ID generator.
     */
    private static Integer baseRequestID = 0;

    /**
     * Request ID of this request.
     */
    private Integer requestID;

    /**
     * Request URL for this request.
     */
    private String requestURL;

    /**
     * Request Time for this request (ms).
     */
    private long requestTime;

    /**
     * Number of strings created for this request.
     */
    private long stringsCreated;

    /**
     * Amount of memory allocated for this request.
     */
    private long memoryAllocated;

    /**
     * Default constructor for the RequestLog class.
     */
    public RequestLog() {
        this.requestID = baseRequestID++;
        this.requestURL = "";
        this.requestTime = 0;
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
    public RequestLog(String requestURL, long requestTime, int stringsCreated, int memoryAllocated) {
        this.requestID = baseRequestID++;
        this.requestURL = requestURL;
        this.requestTime = requestTime;
        this.stringsCreated = stringsCreated;
        this.memoryAllocated = memoryAllocated;
    }

    /**
     * Gets the request ID for this request log.
     * @return request id for this request log.
     */
    public Integer getRequestID() {
        return this.requestID;
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
    public long getRequestTime() {
        return this.requestTime;
    }

    /**
     * Gets the number of strings created for this request log.
     * @return number of strings created for this request log.
     */
    public long getStringsCreated() {
        return this.stringsCreated;
    }

    /**
     * Gets the amount of memory allocated for this request log.
     * @return amount of memory allocated for this request log.
     */
    public long getMemoryAllocated() {
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
    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    /**
     * Sets the number of strings created for this request log.
     * @param stringsCreated the number of strings created for this request log.
     */
    public void setStringsCreated(long stringsCreated) {
        this.stringsCreated = stringsCreated;
    }

    /**
     * Sets the amount of memory allocated for this request log.
     * @param memoryAllocated the amount of memory allocated for this request log.
     */
    public void setMemoryAllocated(long memoryAllocated) {
        this.memoryAllocated = memoryAllocated;
    }

    /**
     * Adds to the number of strings created for this request log.
     * @param newstrings the number of strings to be added to this request log.
     */
    public void addStringsCreated(long newstrings) {
        this.stringsCreated += newstrings;
    }

    /**
     * Adds to the amount of memory allocated for this request log.
     * @param newmemory the amount of memory to be added to this request log.
     */
    public void addMemoryAllocated(long newmemory) {
        this.memoryAllocated += newmemory;
    }

    /**
     * Returns JSON representation of this object via GSON library.
     * @return JSON representation of this object.
     */
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return this.requestID.toString().concat(": ").concat(this.requestURL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (o.getClass() != this.getClass())) {
            return false;
        }
        //Equality testing on fields
        RequestLog obj = (RequestLog) o;
        return this.requestID.equals(obj.requestID) && this.requestURL.equals(obj.requestURL); //.equals on important fields
    }

    @Override
    public int hashCode() {
        return 11 * this.requestID.hashCode() * this.requestURL.hashCode();
    }

}
