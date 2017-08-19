package com.gy.seebook.bean;

import java.util.List;

/**
 * @name SeeBook
 * @class name：com.gy.seebook.bean
 * Author: apple   QQ:954401985
 * Email: guyework@163.com
 * Comment: //TODO
 * Date: 2017-08-19 16:15
 */

public class Recommend {

    /**
     * books : [{"_id":"54cf8ca9c62036b9188b28d2","title":"九幽天帝","author":"给力","shortIntro":"为夺有望冲击神境至宝万物之源,一代强者九幽大帝陨落,重生为一名叫石枫的少年.且看石枫如何凭借前世苏醒的记忆,扼杀各方天才,夺天地造化,踏着累累白骨,回归大帝之位!创了个书友群:14865773喜欢本书的朋友们可以加下!感谢腾讯文学书评团提供书评支持!","cover":"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F80460%2F_80460_567155.jpg%2F","hasCp":true,"latelyFollower":108228,"retentionRatio":28.53,"updated":"2017-08-18T18:52:52.392Z","chaptersCount":2222,"lastChapter":"第2208章"},{"_id":"55b0307a90815a9520977ce1","title":"养鬼为祸","author":"浮梦流年","shortIntro":"我从出生前就给人算计了，五阴俱全，天生招厉鬼，懂行的先生说我活不过七岁，死后是要给人养成血衣小鬼害人的。 外婆为了救我，给我娶了童养媳，让我过起了安生日子，虽然...","cover":"/agent/http://gocache.3g.cn/bookimage/bookpic/78/407378/407378_210_280.jpg?v=20140124","hasCp":true,"latelyFollower":83610,"retentionRatio":18.92,"updated":"2017-08-18T16:39:52.397Z","chaptersCount":2904,"lastChapter":"第三十卷 第二千九百零三章：强攻"},{"_id":"55f435f086dd043d756acf56","title":"女总裁的顶级兵王","author":"天下","shortIntro":"兵王之王叶轩回归都市，保护冰山总裁未婚妻，兄弟相随，各色美女争相而来，且看叶轩如何用一腔热血演绎一场场荡气回肠的故事。","cover":"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F779584%2F_779584_706998.jpg%2F","hasCp":true,"latelyFollower":69921,"retentionRatio":58.63,"updated":"2017-08-18T09:50:23.407Z","chaptersCount":2326,"lastChapter":"正文 第二千三百二十六章  战斗狂人"},{"_id":"579f36e03345106a42ed362d","title":"永恒圣王","author":"雪满弓刀","shortIntro":"天妒之才，谓之天才。天才中龙凤者，可封妖孽。灵根残缺的人族少年，得神秘女子传授一部无上妖典，踏上修行之路。自此，一代妖孽崛起于天荒，令仙魔颤抖，诸圣俯首。\u201c我愿荡尽心中不平，求个念头通达，快意恩仇！\u201d","cover":"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1286282%2F_1286282_707454.jpg%2F","hasCp":true,"latelyFollower":66836,"retentionRatio":24.37,"updated":"2017-08-18T16:02:52.577Z","chaptersCount":662,"lastChapter":"第662章 兄弟情深"},{"_id":"5840af2f9d10342439d7e4ac","title":"心理罪（影视原著）","author":"雷米","shortIntro":"一个喜欢把牛奶和人血搅拌在一起喝下去的杀手，他是有特殊的疾病还是传说中千年不死的吸血鬼？     C市连续发生四起强奸杀人案，被害人都是25至30岁之间的白领，...","cover":"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F508115%2F508115_6e01853c61fa42abb53b8100f184816a.jpg%2F","hasCp":true,"latelyFollower":36691,"retentionRatio":30.3,"updated":"2016-12-01T23:16:00.038Z","chaptersCount":223,"lastChapter":"第223章 心理罪之城市之光(54)"},{"_id":"589675775f5c08a45de7b147","title":"篮坛教皇","author":"兔来割草","shortIntro":"网站NBA数据管理员重生到1997年的美国，一鸣惊人，成为了点废成金的篮坛教皇！书友群号：13774889","cover":"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1463732%2F_1463732_264158.jpg%2F","hasCp":true,"latelyFollower":16561,"retentionRatio":74.12,"updated":"2017-08-19T07:35:05.229Z","chaptersCount":597,"lastChapter":"第598章 【奇葩言论，选秀困惑】"}]
     * ok : true
     */

    private boolean ok;
    private List<BooksBean> books;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public static class BooksBean {
        /**
         * _id : 54cf8ca9c62036b9188b28d2
         * title : 九幽天帝
         * author : 给力
         * shortIntro : 为夺有望冲击神境至宝万物之源,一代强者九幽大帝陨落,重生为一名叫石枫的少年.且看石枫如何凭借前世苏醒的记忆,扼杀各方天才,夺天地造化,踏着累累白骨,回归大帝之位!创了个书友群:14865773喜欢本书的朋友们可以加下!感谢腾讯文学书评团提供书评支持!
         * cover : /agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F80460%2F_80460_567155.jpg%2F
         * hasCp : true
         * latelyFollower : 108228
         * retentionRatio : 28.53
         * updated : 2017-08-18T18:52:52.392Z
         * chaptersCount : 2222
         * lastChapter : 第2208章
         */

        private String _id;
        private String title;
        private String author;
        private String shortIntro;
        private String cover;
        private boolean hasCp;
        private int latelyFollower;
        private double retentionRatio;
        private String updated;
        private int chaptersCount;
        private String lastChapter;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getShortIntro() {
            return shortIntro;
        }

        public void setShortIntro(String shortIntro) {
            this.shortIntro = shortIntro;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public boolean isHasCp() {
            return hasCp;
        }

        public void setHasCp(boolean hasCp) {
            this.hasCp = hasCp;
        }

        public int getLatelyFollower() {
            return latelyFollower;
        }

        public void setLatelyFollower(int latelyFollower) {
            this.latelyFollower = latelyFollower;
        }

        public double getRetentionRatio() {
            return retentionRatio;
        }

        public void setRetentionRatio(double retentionRatio) {
            this.retentionRatio = retentionRatio;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public int getChaptersCount() {
            return chaptersCount;
        }

        public void setChaptersCount(int chaptersCount) {
            this.chaptersCount = chaptersCount;
        }

        public String getLastChapter() {
            return lastChapter;
        }

        public void setLastChapter(String lastChapter) {
            this.lastChapter = lastChapter;
        }
    }
}
