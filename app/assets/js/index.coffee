# this sends an HTTP POST request with a JSON body
# it makes use of jQuery's promises... so easy with 
# coffeescript
sanitize = (str) -> 
    str.replace(/</g, "&lt;").replace(/>/g,"&gt;")

reqCounter = 0
send = (url, obj) ->
    json = JSON.stringify obj
    
    reqCounter++

    # so we don't lose the value in multiple async calls
    count = reqCounter
    out("#{count}) Sending: #{sanitize(json)}")
    p = $.ajax 
        type: "POST"
        url: url 

        # required for play framework to recognize what
        # the data type is, by default jQuery sends
        # application/x-www-form-urlencoded
        contentType: "application/json; charset=UTF-8"

        # changes the request accept header to 
        # expect an application/json reesponse
        dataType: "json"

        # send a String...
        data: json
    p.success (resp) ->
        json = JSON.stringify resp
        out "#{count}) Done. Response: #{sanitize json}"
    p.fail (xhr, type, message)->
        out "FAIL: #{xhr.status} #{url} #{message}"

out = (content) ->
    $('#output').append("#{content}\n")

# jquery DOM Ready... 
$ -> 
    $('#sendName').on "click", -> 
        name = $('#name').val()
        url = "/echoName"
        send url, name: name

    $("#sendMsg").on "click", ->
        sender = $("#sender").val()
        msg = $('#message').val()
        url = "/echoMsg"
        send url, sender: sender, message: msg
         
