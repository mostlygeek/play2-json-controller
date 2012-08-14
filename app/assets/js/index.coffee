# this sends an HTTP POST request with a JSON body
# it makes use of jQuery's promises... so easy with 
# coffeescript
send = (url, obj) ->
    # this 
    $.ajax 
        type: "POST"
        url: url 

        # required for play framework to recognize what
        # the data type is, by default jQuery sends
        # application/x-www-form-urlencoded

        contentType: "application/json"
        data: JSON.stringify obj

$ -> 
    count = 0
    out = (content) ->
        count++ 
        $('#count').text(count)
        $('#output').html(content)

    $('#sendName').on "click", -> 
        p = send "/echoName", name: "mostlygeek"
        p.success (resp) -> 
            out(resp)
    
