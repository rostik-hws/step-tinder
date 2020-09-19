<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
<#--  <link rel="icon" href="img/favicon.ico">-->

  <title>Chat</title>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
        integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
  <link href="../css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="../css/style.css">
</head>
<body>

<div class="container">
  <div class="row">
    <div class="chat-main col-6 offset-3">
      <div class="col-md-12 chat-header">
        <div class="row header-one text-white p-1 border border-dark">
          <div class="col-md-6 name pl-2">
            <i class="fa fa-comment"></i>
            <h6 class="ml-1 mb-0">${receiver_name}</h6>
          </div>
          <div class="col-md-6 options text-right pr-0">
            <a href="/likes">
            <i class="fa fa-times hover text-center pt-1"></i>
            </a>
          </div>
        </div>
        <div class="row header-two w-100 border-right border-left border-dark">
          <div class="col-md-6 options-left pl-1">
            <i class="fa fa-video-camera mr-3"></i>
          </div>
          <div class="col-md-6 options-right text-right pr-2">
          </div>
        </div>
      </div>
      <div class="chat-content">
        <div class="col-md-12 chats pt-3 pl-2 pr-3 pb-3 border-right border-left border-dark">
          <ul class="p-0">
              <#list messages as message>
                  <#if message.to_id == receiver_id>
                    <li class="send-msg float-right mb-2">
                      <p class="pt-1 pb-1 pl-2 pr-2 m-0 rounded">
                          ${message.content}
                      </p>
                    </li>
                  <#else>
                    <li class="receive-msg float-left mb-2">
                      <div class="sender-img">
                        <img src="${receiver_photo_url}" alt="user-photo" class="float-left">
                      </div>
                      <div class="receive-msg-desc float-left ml-2">
                        <p class="bg-white m-0 pt-1 pb-1 pl-2 pr-2 rounded">
                            ${message.content}
                        </p>
                        <span class="receive-msg-time">${message.date}</span>
                      </div>
                    </li>
                  </#if>
              </#list>
          </ul>
        </div>
        <div class="col-md-12 p-2 msg-box border border-dark">
          <div class="row">
            <div class="col-md-1 options-left">
              <i class="fa fa-smile-o"></i>
            </div>
            <div class="col-md-7 pl-0">
              <form method="post">
                <input type="text" name="message" class="border-0" placeholder="Send message"/>
              </form>
            </div>
            <div class="col-md-3 text-right options-right">
              <i class="fa fa-picture-o mr-2"></i>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>