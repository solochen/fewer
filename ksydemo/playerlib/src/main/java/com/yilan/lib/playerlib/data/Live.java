package com.yilan.lib.playerlib.data;

import java.io.Serializable;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class Live implements Serializable{

    private String live_id = "";
    private LiveStream live_stream = new LiveStream();

    public String getLive_id() {
        return live_id;
    }

    public void setLive_id(String live_id) {
        this.live_id = live_id;
    }

    public LiveStream getLive_stream() {
        return live_stream;
    }

    public void setLive_stream(LiveStream live_stream) {
        this.live_stream = live_stream;
    }

    public class LiveStream implements Serializable{
        private int ha_stream = 0;
        private String default_res = "";
        private int default_buffer_ms = 0;
        private String avformat = "";
        private LiveUrlList main_list;
        private LiveUrlList backup_list;

        public int getHa_stream() {
            return ha_stream;
        }

        public void setHa_stream(int ha_stream) {
            this.ha_stream = ha_stream;
        }

        public String getDefault_res() {
            return default_res;
        }

        public void setDefault_res(String default_res) {
            this.default_res = default_res;
        }

        public int getDefault_buffer_ms() {
            return default_buffer_ms;
        }

        public void setDefault_buffer_ms(int default_buffer_ms) {
            this.default_buffer_ms = default_buffer_ms;
        }

        public String getAvformat() {
            return avformat;
        }

        public void setAvformat(String avformat) {
            this.avformat = avformat;
        }

        public LiveUrlList getMain_list() {
            return main_list;
        }

        public void setMain_list(LiveUrlList main_list) {
            this.main_list = main_list;
        }

        public LiveUrlList getBackup_list() {
            return backup_list;
        }

        public void setBackup_list(LiveUrlList backup_list) {
            this.backup_list = backup_list;
        }

        public class LiveUrlList implements Serializable{

            private String high = "";
            private String medium = "";
            private String low = "";
            private String onlyaudio = "";

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getOnlyaudio() {
                return onlyaudio;
            }

            public void setOnlyaudio(String onlyaudio) {
                this.onlyaudio = onlyaudio;
            }
        }


    }
}
