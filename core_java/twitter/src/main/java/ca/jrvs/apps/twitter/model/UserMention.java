package ca.jrvs.apps.twitter.model;

public class UserMention {
    int userId;

    String userIdString;

    int[] indices;

    String referencedUser;

    String screenName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserIdString() {
        return userIdString;
    }

    public void setUserIdString(String userIdString) {
        this.userIdString = userIdString;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public String getReferencedUser() {
        return referencedUser;
    }

    public void setReferencedUser(String referencedUser) {
        this.referencedUser = referencedUser;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
