package com.fnhelper.photo.beans;

public class LoginBean{


    /**
     * code : 100
     * info : null
     * data : {"ID":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sToken":"c748cf4250af42ca97862b1919cd9041","sNickName":"好疯狂的石头","sPhone":null,"sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","sWeiXinNo":null,"sPhotoName":null,"sIntroduce":null}
     */

    private int code;
    private String info;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : EC5AD1D4674E4EB7AA68DD5939BC5BD6
         * sToken : c748cf4250af42ca97862b1919cd9041
         * sNickName : 好疯狂的石头
         * sPhone : null
         * sHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132
         * sWeiXinNo : null
         * sPhotoName : null
         * sIntroduce : null
         */

        private String ID;

        private String sToken;
        private String sNickName;
        private String sPhone;
        private String sLinkPhone;
        private String sHeadImg;
        private String sWeiXinNo;
        private String sPhotoName;
        private String sIntroduce;
        /**
         * HtmlPageUrl : {"about":"http://153h79422m.imwork.net:10354/open/html/about","clientPower":"http://153h79422m.imwork.net:10354/open/html/clientPower","drawInstruction":"http://153h79422m.imwork.net:10354/open/html/drawInstruction","link":"http://153h79422m.imwork.net:10354/open/html/link","userAgreement":"http://153h79422m.imwork.net:10354/open/html/userAgreement"}
         */


        private HtmlPageUrlBean HtmlPageUrl;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getSToken() {
            return sToken;
        }

        public void setSToken(String sToken) {
            this.sToken = sToken;
        }

        public String getSNickName() {
            return sNickName;
        }

        public void setSNickName(String sNickName) {
            this.sNickName = sNickName;
        }

        public String getSPhone() {
            return sPhone;
        }

        public String getsLinkPhone() {
            return sLinkPhone;
        }

        public void setsLinkPhone(String sLinkPhone) {
            this.sLinkPhone = sLinkPhone;
        }

        public void setSPhone(String sPhone) {
            this.sPhone = sPhone;
        }

        public String getSHeadImg() {
            return sHeadImg;
        }

        public void setSHeadImg(String sHeadImg) {
            this.sHeadImg = sHeadImg;
        }

        public String getSWeiXinNo() {
            return sWeiXinNo;
        }

        public void setSWeiXinNo(String sWeiXinNo) {
            this.sWeiXinNo = sWeiXinNo;
        }

        public String getSPhotoName() {
            return sPhotoName;
        }

        public void setSPhotoName(String sPhotoName) {
            this.sPhotoName = sPhotoName;
        }

        public String getSIntroduce() {
            return sIntroduce;
        }

        public void setSIntroduce(String sIntroduce) {
            this.sIntroduce = sIntroduce;
        }

        public HtmlPageUrlBean getHtmlPageUrl() {
            return HtmlPageUrl;
        }

        public void setHtmlPageUrl(HtmlPageUrlBean HtmlPageUrl) {
            this.HtmlPageUrl = HtmlPageUrl;
        }

        public static class HtmlPageUrlBean {
            /**
             * about : http://153h79422m.imwork.net:10354/open/html/about
             * clientPower : http://153h79422m.imwork.net:10354/open/html/clientPower
             * drawInstruction : http://153h79422m.imwork.net:10354/open/html/drawInstruction
             * link : http://153h79422m.imwork.net:10354/open/html/link
             * userAgreement : http://153h79422m.imwork.net:10354/open/html/userAgreement
             */

            private String about;
            private String clientPower;
            private String drawInstruction;
            private String link;
            private String userAgreement;
            private String shareLink;
            public String getShareLink() {
                return shareLink;
            }

            public void setShareLink(String shareLink) {
                this.shareLink = shareLink;
            }
            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getClientPower() {
                return clientPower;
            }

            public void setClientPower(String clientPower) {
                this.clientPower = clientPower;
            }

            public String getDrawInstruction() {
                return drawInstruction;
            }

            public void setDrawInstruction(String drawInstruction) {
                this.drawInstruction = drawInstruction;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getUserAgreement() {
                return userAgreement;
            }

            public void setUserAgreement(String userAgreement) {
                this.userAgreement = userAgreement;
            }
        }
    }
}
