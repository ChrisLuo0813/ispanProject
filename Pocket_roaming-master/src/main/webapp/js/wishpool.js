var type = 1;
var list = [];
var len;
var searchName = "";
allforthcol = []
allforthcol[0] = document.getElementById('firstcol')
allforthcol[1] = document.getElementById('secondcol')
allforthcol[2] = document.getElementById('thirdcol')
allforthcol[3] = document.getElementById('forthcol')
async function accessAndRefresh() {
    switch (type) {
        case 1:
            var temp = $.ajax({
                type: "get",
                async:!1,
                url: "/api/wishpool/recent"
            });            
            console.log(temp);
            break;
        case 2:
            var temp = $.ajax({
                type: "get",
                async:!1,
                url: "/api/wishpool/popular"
            });
            break;
        case 3:
            var temp = $.ajax({
                type: "post",
                async:!1,
                url: "/api/wishpool/itamName",
                data: {
                    itemName: searchName
                }
            });
            break;
    }

    list = temp["responseJSON"];

    fillUI();
}
function fillUI() {
    // $(".row").empty();
    $("#firstcol").empty();
    $("#secondcol").empty();
    $("#thirdcol").empty();
    $("#forthcol").empty();
    if (list.length > 12) {
        len = 12;
    } else {
        len = list.length;
    }
    // allforthcol=[]
    for (var i = 0; i < (len % 4 == 0 ? len / 4 : (len / 4) + 1); i++) {
        // $(".row").append(`<div class="col-3 mb-3 " id = "col${i}"></div>`);
        for (var k = 0; k < 4; k++) {

            var index = k + i * 4;
            if (list[index].item_description == null) {
                list[index].item_description = "";
            }
            // console.log(allforthcol[k])
            tmp = '<div class="card mb-5"><div>'
                + '<img src="' + list[index].item_photo_url + '" class="card-img-top col-12" alt="...">'
                // +'</div></div>'
                + '<div class="card-body" id="' + list[index].wishlist_id + '">'
                + '<p class="card-text fs-3">' + list[index].item_name + '</p>'
                + '<p class="card-text">' + list[index].item_description + '</p>'
                + '<i class="bi bi-hand-thumbs-up-fill h4 fs-4 text-primary like"></i>'
                + '<p style="display: inline;" class="text-primary fs-4 mx-3">' + list[index].item_likes + '</p>'
                + '</div></div>' + '</div></div>'

            // $tmp=$(tmp)
            // $(`#col${i}`).append($tmp)
            // $(allforthcol[k]).append($tmp)
            // allforthcol[k].appendChild(tmp)
            // console.log(allforthcol[k])
            // console.log(k)
            allforthcol[k].insertAdjacentHTML("beforeend", tmp)
            // console.log(allforthcol[k])
        }

    }
}
function refreshUI() {

    var documentElement = document.documentElement;

    if (documentElement.scrollTop + 10 >= documentElement.scrollHeight - documentElement.clientHeight) {
        switch (type) {
            case 1:
                var temp = $.ajax({
                    type: "get",
                    async: !1,
                    url: "/api/wishpool/recent"
                });
                break;
            case 2:
                var temp = $.ajax({
                    type: "get",
                    async: !1,
                    url: "/api/wishpool/popular"
                });
                break;
            case 3:
                var temp = $.ajax({
                    type: "post",
                    url: "/api/wishpool/itamName",
                    async: !1,
                    data: {
                        itemName: searchName
                    }
                });
                break;
        }

        var newlist = temp;

        for (var i = 0; i < 2; i++) {
            // var row = document.querySelector(".row");
            // row.insertAdjacentHTML('beforeend', `<div class="col-3 mb-3 " id = "col${i + 3}"></div>`)
            for (var k = 0; k < 4; k++) {

                var index = k + i * 4;
                if (newlist['responseJSON'][index + 12]['item_description'] == null) {
                    newlist['responseJSON'][index + 12]['item_description'] = "";
                }
                var tmp = '<div class="card mb-5"><div>'
                    + '<img src="' + newlist['responseJSON'][index + 12]['item_photo_url'] + '" class="card-img-top col-12" alt="...">'
                    // +'</div></div>'
                    + '<div class="card-body" id="' + newlist['responseJSON'][index + 12]['wishlist_id'] + '">'
                    + '<p class="card-text fs-3">' + newlist['responseJSON'][index + 12]['item_name'] + '</p>'
                    + '<p class="card-text">' + newlist['responseJSON'][index + 12]['item_description'] + '</p>'
                    + '<i class="bi bi-hand-thumbs-up-fill h4 fs-4 text-primary like"></i>'
                    + '<p style="display: inline;" class="text-primary fs-4 mx-3">' + newlist['responseJSON'][index + 12]["item_likes"] + '</p>'
                    + '</div></div>' + '</div></div>'

                // $tmp=$(tmp)
                // console.log(i)
                // console.log(allforthcol[i+3])
                // $(`#col${i + 3}`).append($tmp)
                // allforthcol[i+3].appendChild(tmp)
                // allforthcol[i+3].appendChild("beforeend",tmp)
                allforthcol[k].insertAdjacentHTML("beforeend", tmp)
                // document.getElementById('forthcol').appendChild(tmp)
                // $(allforthcol[i+3]).append($tmp)
            }

        }


    }
}

$("body").on("click", ".like", async function () {

    $.ajax({
        type: "put",
        async:!1,
        url: "/api/wishpool/like/" + this.parentNode.id
    })

    accessAndRefresh();
})

$(function () {

    $("#recent").click(function () {
        type = 1;
        accessAndRefresh();
    })
    $("#popular").click(function () {
        type = 2;
        accessAndRefresh();
    })
    $("#searchwishpoolinput").click(function () {
        type = 3;
        searchName = $("#searchwishpool").val();
        accessAndRefresh();
    })
    $("#addwish").click(function () {
        location.href = "./member_addwishlists.html"
    })
    accessAndRefresh();
    window.addEventListener('scroll', refreshUI);

})  
