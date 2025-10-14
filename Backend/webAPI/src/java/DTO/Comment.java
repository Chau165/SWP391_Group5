package DTO;

import java.util.Date;

public class Comment {
    private int commentId;
    private int userId;
    private int stationId;
    private Integer swapId; // new: reference to Swap_Transaction.Swap_ID
    private String content;
    private Date timePost;

    // optional fields for admin view
    private String userFullName;
    private String userRole;

    public Comment() {}

    public Comment(int commentId, int userId, int stationId, String content, Date timePost) {
        this.commentId = commentId;
        this.userId = userId;
        this.stationId = stationId;
        this.content = content;
        this.timePost = timePost;
    }

    public Comment(int commentId, int userId, Integer swapId, String content, Date timePost, String userFullName, String userRole) {
        this.commentId = commentId;
        this.userId = userId;
        this.swapId = swapId;
        this.content = content;
        this.timePost = timePost;
        this.userFullName = userFullName;
        this.userRole = userRole;
    }

    public int getCommentId() { return commentId; }
    public void setCommentId(int commentId) { this.commentId = commentId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getStationId() { return stationId; }
    public void setStationId(int stationId) { this.stationId = stationId; }

    public Integer getSwapId() { return swapId; }
    public void setSwapId(Integer swapId) { this.swapId = swapId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getTimePost() { return timePost; }
    public void setTimePost(Date timePost) { this.timePost = timePost; }

    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
}
