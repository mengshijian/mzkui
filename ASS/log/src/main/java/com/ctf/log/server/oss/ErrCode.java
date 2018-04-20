package com.ctf.log.server.oss;

public class ErrCode {
    public static final String PROTO_MD5 = "47c0d5b575340b83051fb50db032c5fb";
    public static final String PROTO_HASH = "47c0d5b5";
    public static final String CODES_MD5 = "9be5a3358f1a0c0f0620a96eb342da7b";
    public static final String CODES_HASH = "9be5a335";
    public static final String CODE_VER = "1.2";

    public ErrCode() {
    }

    public static enum ServerCode {
        ERR_OK(1, 1, "err ok", "成功"),
        USER_NOT_EXIST(1, 0, "user not exist", "用户不存在"),
        ERR_PROTO_NOT_COMPATIABLE(2, 0, "proto not compatiable", "proto 版本不一致"),
        ERR_CODE_NOT_COMPATIABLE(3, 0, "code not compatiable", "软件版本不一致"),
        USER_WRONG_PASSWORD(4, 0, "user wrong password", "密码错误");

        short value;
        int defType;
        String enErrMsg;
        String cnErrMsg;

        private ServerCode(int value, int defType, String enErrMsg, String cnErrMsg) {
            this.value = (short) value;
            this.defType = defType;
            this.enErrMsg = enErrMsg;
            this.cnErrMsg = cnErrMsg;
        }

        public short value() {
            return this.value;
        }

        public int defType() {
            return this.defType;
        }

        public String enErrMsg() {
            return this.enErrMsg;
        }

        public String cnErrMsg() {
            return this.cnErrMsg;
        }
    }

}
