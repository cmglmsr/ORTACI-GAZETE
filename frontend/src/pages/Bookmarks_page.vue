<template>
  <div>
    <Navbar></Navbar>
    <div class="container-fluid">
      <div class="row no-shadow" style="width: 110%; height: 100%">
        <div class="col-2" style="display: inline">
          <Res_sidebar></Res_sidebar>
        </div>
        <div :class="columnClass" class="profile-page-card">
          <Tab_nav
            :tabs="['Bookmarks']"
            :selected="selected"
            @selected="setSelected"
          ></Tab_nav>
          <div v-if="selected === 'Bookmarks'">
            <div class="row no-shadow adjustment">
              <Bookmarks
                      v-for="bookmark in bookmarks_list"
                      :id="bookmark?.id"
                      :imageUrl="bookmark?.img"
                      :date="bookmark.publishDate ? formatDate(bookmark.publishDate) : 'N/A'"
                      :source="bookmark.source"
                      :header="bookmark.title"
                      :content="bookmark.content"
                      class="mb-5">
              </Bookmarks>
            </div>
          </div>
        </div>
        <div
          class="col-3 d-none d-md-block"
          style="background-color: #11101d; display: inline;"
        >
            <Latest_headings
                    :latestNews = "latestNews"
            ></Latest_headings>
        </div>
      </div>
    </div>
    <error_popup></error_popup>
  </div>
</template>

<script setup>
import Latest_headings from "../resp_components/latest_headings/Latest_headings.vue";
import Bookmarks from "../resp_components/profile/Bookmarks.vue";
import Tab_nav from "../resp_components/tabs/Tab_nav_profile.vue";
import Navbar from "../components/navbar/Navbar.vue";
import Res_sidebar from "../resp_components/sidebar/Res_sidebar.vue";
</script>

<script>
import "./feed.css";
import "primeicons/primeicons.css";
import { axiosInstance, noAuthAxiosInstance } from "@/utils";
import moment from "moment/moment";
import { eventBus } from "../event-bus";
import error_popup from "../resp_components/popups/error_popup.vue";
export default {
  name: "bookmarks-page",
  data() {
    return {
      selected: "Bookmarks",
      alignment: "",
      news_id: 0,
      bookmarks_list: [],
      latestNews: []
    };
  },
  components:{
    error_popup
  },
  computed: {
    columnClass() {
      return this.isWide ? "col-10" : "col-7";
    },
  },
  methods: {
    set_alignment(tab_alignment) {
      this.alignment = tab_alignment;
    },
    setSelected(tab) {
      this.selected = tab;
    },
    formatDate(dateInput, format = "DD.MM.YYYY") {
      return moment(dateInput).format(format);
    },
  },
  async mounted() {
      const response = await axiosInstance.get(`/user/bookmarks`);
      this.bookmarks_list = response.data;
      try {
          const resp = await noAuthAxiosInstance.get(`/news?pageNum=0&pageSize=5`);
          this.latestNews = resp.data
      }
      catch (e) {
        eventBus.emit("api-error", "An unexpected error occurred. Please try again later.");
      }
  },
};
</script>

<style scoped>
.profile-page-card {
  display: inline;
  height: 100%;
}
.adjustment {
  width: 75%;
  height: 60%;
  margin-top: 1vw;
  margin-left: 4vw;
}

.h2-title {
  font-size: 15px;
}

.users-comment-to-post {
  font-family: "Poppins", sans-serif;
  color: #11101d;
  font-weight: 600;
  font-size: 18px;
  margin-bottom: 50px;
}

.icon-buttons-comment-a {
  padding: 0.5vw;
  border: none;
  border-radius: 2vw;
  font-weight: 500;
  background-color: #bacdd8;
  font-size: 0.7vw;
}

.add-comment-popup {
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
  position: fixed;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
  position: fixed;
  z-index: 9999;
}

.add-comment-popup .add-comment-overlay {
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 1;
  transition: opacity 100ms ease-in-out 200ms;
  position: fixed;
  z-index: 9999;
}

.add-comment-popup .add-comment-popup-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 95%;
  max-width: 650px;
  background: #fff;
  padding: 25px;
  border-radius: 20px;
  box-shadow: 0px 2px 2px 5px rgba(0, 0, 0, 0.05);
  transition: all 300ms ease-in-out;
  position: fixed;
  z-index: 9999;
}

.add-comment-popup .add-comment-popup-content .add-comment-controls-versions {
  display: flex;
  justify-content: space-between;
  margin: 20px 0px 0px;
}

.add-comment-popup
  .add-comment-popup-content
  .add-comment-controls-close
  .add-comment-close-btn {
  background: transparent;
  color: #11101d;
}

.name {
  font-size: 24px;
  font-weight: 600;
  margin-top: 16px;
  color: #11101d;
}

.name-list-element {
  font-size: 18px;
  font-weight: 800;
  margin-top: 10px;
  color: #11101d;
}

.Navbar-text {
  font-family: "Poppins", sans-serif;
  color: #fff;
  font-size: 1.2vw;
  text-align: center;
  font-weight: 400;
  padding-top: 1vw;
  padding-right: 7vw;
}

.stars {
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
  gap: 1vw;
  margin-top: 10vw;
  padding-right: 40vw;
  padding-left: -40vw;
}

.stars i {
  color: #e6e6e6;
  font-size: 2vw;
  cursor: pointer;
}

.icon-buttons {
  text-decoration: none;
  padding: 0.9vw 1.2vw;
  border: none;
  border-radius: 2vw;
  font-weight: 600;
  font-size: 0.9vw;
  background-color: #e0efff;
  margin-left: 3vw;
  margin-bottom: -3vw;
  color: #11101d;
}

.p-content {
  height: auto !important;
  font-size: "3vw";
  color: #11101d;
  line-height: 150%;
}

.name {
  font-size: 24px;
  font-weight: 600;
  margin-top: 16px;
  color: #11101d;
}

.card-details {
  padding: 1.5vw 1.5vw 2vw;
}

.tag {
  margin-top: 0.5vw;
  margin-left: 0.5vw;
  padding: 0.5vw 0.5vw;
  border: 0.2vw solid #e5eaed;
  border-radius: 1vw;
  font-size: 1vw;
  font-weight: 600;
  color: #11101d;
  width: auto;
  align-items: center;
}

.clicked1 {
  color: #a42323;
}

.clicked2 {
  color: #4477cf;
}
.star_clicked {
  color: #11101d;
  font-size: 30px;
  cursor: pointer;
}

.generate-ai-popup {
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
  position: fixed;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
}

.add-comment-popup {
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
  position: fixed;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
}

.generate-ai-popup .generate-ai-overlay {
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 1;
  transition: opacity 100ms ease-in-out 200ms;
}

.add-comment-popup .add-comment-overlay {
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 1;
  transition: opacity 100ms ease-in-out 200ms;
}

.generate-ai-popup .generate-ai-popup-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 95%;
  max-width: 450px;
  background: #fff;
  padding: 25px;
  border-radius: 20px;
  box-shadow: 0px 2px 2px 5px rgba(0, 0, 0, 0.05);
  transition: all 300ms ease-in-out;
}

.add-comment-popup .add-comment-popup-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 95%;
  max-width: 450px;
  background: #fff;
  padding: 25px;
  border-radius: 20px;
  box-shadow: 0px 2px 2px 5px rgba(0, 0, 0, 0.05);
  transition: all 300ms ease-in-out;
}

.generate-ai-popup .generate-ai-popup-content h2 {
  margin: 10px 0px;
  font-size: 25px;
  color: #11101d;
  text-align: center;
}

.add-comment-popup .add-comment-popup-content h2 {
  margin-top: 15px;
  margin-bottom: 60px;
  font-size: 25px;
  color: #11101d;
  text-align: center;
}

.generate-ai-popup .generate-ai-popup-content .generate-ai-controls-versions {
  display: flex;
  justify-content: space-between;
  margin: 20px 0px 0px;
}

.add-comment-popup .add-comment-popup-content .add-comment-controls-versions {
  display: flex;
  justify-content: space-between;
  margin: 20px 0px 0px;
}

.generate-ai-popup .generate-ai-popup-content .generate-ai-controls-close {
  display: flex;
  margin-left: 38%;
  margin-top: 20px;
}

.add-comment-popup .add-comment-popup-content .add-comment-controls-close {
  display: flex;
  margin-left: 38%;
  margin-top: 20px;
}

.add-comment-popup
  .add-comment-popup-content
  .add-comment-controls-versions
  button {
  padding: 10px 20px;
  border: none;
  outline: none;
  font-size: 15px;
  border-radius: 20px;
  cursor: pointer;
}

.generate-ai-popup
  .generate-ai-popup-content
  .generate-ai-controls-close
  button {
  padding: 10px 20px;
  border: none;
  outline: none;
  font-size: 15px;
  border-radius: 20px;
  cursor: pointer;
}

.generate-ai-popup
  .generate-ai-popup-content
  .generate-ai-controls-versions
  button {
  padding: 10px 20px;
  border: none;
  outline: none;
  font-size: 15px;
  border-radius: 20px;
  cursor: pointer;
}

.generate-ai-popup
  .generate-ai-popup-content
  .generate-ai-controls-close
  .generate-ai-close-btn {
  background: transparent;
  color: #11101d;
}

.add-comment-popup
  .add-comment-popup-content
  .add-comment-controls-close
  .add-comment-close-btn {
  background: transparent;
  color: #11101d;
}

.add-comment-popup
  .add-comment-popup-content
  .add-comment-controls-versions
  .add-comment-submit-btn {
  background: #11101d;
  color: #fff;
}

.generate-ai-popup
  .generate-ai-popup-content
  .generate-ai-controls-versions
  .generate-ai-submit-btn {
  background: #11101d;
  color: #fff;
}

.popup.active {
  top: 0px;
  transition: top 0ms ease-in-out 0ms;
}

.popup.active .overlay {
  opacity: 1;
  transition: all 300ms ease-in-out;
}

.pop.active .popup-content {
  transform: translate(-50%, -50%) scale(1);
  opacity: 1;
}

.input-field {
  display: flex;
  flex-direction: column;
  position: relative;
}

.login-input {
  height: 45px;
  width: 100%;
  background: transparent;
  border: none;
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
  outline: none;
  margin-bottom: 40px;
  color: #40414a;
}

.input-field .login-label {
  position: absolute;
  top: 10px;
  left: 10px;
  pointer-events: none;
  transition: 0.5s;
}

.name-popup {
  font-size: 18px;
  font-weight: 600;
  margin-top: 12px;
  padding-bottom: 19px;
  color: #11101d;
  text-align: center;
}

@media screen and (max-width: 768px) {
  .adjustment {
    width: 135%;
    height: 60%;
    margin-top: 1vw;
    margin-left: -2vw;
  }

  .profile-page-card {
    display: inline;
    height: 100%;
    width: 60%;
  }
}
</style>
