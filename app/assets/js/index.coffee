# this sends an HTTP POST request with a JSON body
# it makes use of jQuery's promises... so easy with 
# coffeescript
send = (url, obj) ->
    # this 
    json = JSON.stringify obj
    out("Sending: #{json}")
    $.ajax 
        type: "POST"
        url: url 

        # required for play framework to recognize what
        # the data type is, by default jQuery sends
        # application/x-www-form-urlencoded

        contentType: "application/json"
        data: json

out = (content) ->
    $('#output').append("#{content}\n")

$ -> 
    $('#sendName').on "click", -> 
        name = $('#name').val()
        url = "/echoName"
        p = send url, name: name
        p.success (resp) -> 
            out "Done: got #{resp}"
    
