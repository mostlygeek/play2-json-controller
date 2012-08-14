# this sends an HTTP POST request with a JSON body
# it makes use of jQuery's promises... so easy with 
# coffeescript
sanitize = (str) -> 
    str.replace(/</g, "&lt;").replace(/>/g,"&gt;")

send = (url, obj) ->
    json = JSON.stringify obj
    out("Sending: #{sanitize(json)}")
    $.ajax 
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

out = (content) ->
    $('#output').append("#{content}\n")

$ -> 
    $('#sendName').on "click", -> 
        name = $('#name').val()
        url = "/echoName"
        p = send url, name: name
        p.success (resp) -> 
            json = JSON.stringify(resp)
            out "Done, response: #{sanitize(json)}"
    
