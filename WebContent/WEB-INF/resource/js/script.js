/**
 * Created by shashank on 2/29/16.
 */
(function{
    function makeAjax{
        var url = '../api/';
        xhr = new XMLHttpRequest();
        xhr.open('GET',url,true);
        xhr.send();
        xhr.addEventListener('readystatechange',function(){
            if(xhr.readyState === 4)
            {
                if(xhr.status === 200)
                {
                    var jsonObj = JSON.parse(xhr.responseText);
                    console.log(jsonObj);
                }
                else {
                    console.log(xhr);
                }

            }else {
                //page not ready
            }

        },false);
    }
    //document.getElementById('btn').addEventListener('click',makeAjax,false);
})();