function getproduct(item_id) {
    return $.ajax({
        type: "get",
        async:!1,
        url: `/product${item_id}`
    })}
function AjaxgetRegister() {
    return $.ajax({
        type: "get",
        url: "/getRegister",
        async: !1,
        success: function(res) {
            //			console.log(res["member_nickname"])
        },
        error: function(err) { console.log(err) },
    })
}
function creatchatroom(poster,viewer){
    return $.ajax({
		url:"/api/chat/creatchatroom/"+poster+"/"+viewer,
		type:"PUT",
		async:!1,
		success:function(res){},
		error:function(err){console.log(err)},
		});
}
function downloadAndRefreshUI(item_id) {
    var p = $.ajax({
        type: "get",
        async:!1,
        url: `/product${item_id}`
    })
    item = p["responseJSON"];
    console.log(item);
    // console.log("原po: "+item.litems[0].member_id);
    // poster = item.litems[0].member_id
    refreshUI(item);
}
$("body").on("click",".recommendCommodity",function(){
    console.log(this);
    localStorage.setItem("item_id", $(this).attr("data-value"));
                    location.href = "./product.html";
   })
function refreshUI(item) {
    $(".recommend").empty();
    for(i=0;i<item.recommend.length;i++){
        let item_id=item.recommend[i].item_id;
        let item_name=item.recommend[i].item_name;
        let item_description=item.recommend[i].item_description;
        let item_price=item.recommend[i].item_price;
        let picture_url=item.recommend_picture[i].picture_url;  
        console.log(item_id);           
        let recommendTemplate=`<div class="col-3">
        <div class="px-2 py-3">
            <div class="card outerBox">
            <a><img src="../img/${picture_url}" data-value="${item_id}" class="img-fluid rounded-3 enterProduct" style="height:300px;width:auto;margin:auto" ></a>
                <p class="text-center card-title fs-2"> ${item_name}</p>
                <div class="mx-4">
                    <span class="ellipsis2">${item_description}</span>
                </div>
                <div class="px-3 py-3">
                    <div class="d-flex justify-content-between mx-3 my03">
                        <span>${item_price}</span>                                              
                        <button class="addCart" name="${item_id}">加入購物車</button>
                    </div>
                </div>
            </div>
        </div>
    </div>  `
         $(".recommend").append(recommendTemplate);  
    } 
 
    console.log(item)
    if(item.member.seven11_address==null){item.member.seven11_address=""}
    if(item.member.ok_address==null){item.member.ok_address=""}
    if(item.member.family_address==null){item.member.family_address=""}
    $(".productDetail").empty();
    let template = ` 
<div class="col-4 ">
    <div class="px-3 py-3 ">
        <img src="../img/${item.item_picture.picture_url}"style="height:auto;width:500px;" ">
    </div>
</div>
<div class="col-8 px-4 py-4">
    <h1 class="py-4">${item.litems[0].item_name}</h1>
    <h2 class="py-4">$${item.litems[0].item_price}</h2>
    <div class="my-4">
        <p class="h3">${item.litems[0].item_description}</p>               
    </div>
    <br><br>
    <div class="row my-3">                
        <div class="col-4">
            <p class="h3">${item.member.seven11_address}</p>
            <p class="h3">${item.member.ok_address}</p>
            <p class="h3">${item.member.family_address}</p>
        </div>
        <div class="col-4">
            <p class="h3">信用卡轉帳/帳戶匯款</p>
        </div>
    </div>
    <div class="mt-auto position-absolute  end-0 mx-4  bottom-0  py-5 btn-group ">
        <button class="btn btn-info text-white border-3 border-dark addCart"><p class="fs-1">加入購物車</p</button>
        <button class="btn btn-secondary text-white border-3 border-dark" id="rentfile"><p class="fs-1">查看出租者</p></button>
        <button class="btn btn-secondary text-white border-3 border-dark" id="contactposter"><p class="fs-1">聯絡出租者</p></button>
    </div>
</div>`

let template2 = `<div class="col-5">
<div class="my-5 py-3 container text-center">
<img src="../img/${item.item_picture.picture_url}" class="img-fluid w-100">
</div>
</div>
<div class="col-6 px-4 py-4">
<div class="row">
    <div class=" d-flex justify-content-between">
        <span class="py-4 fs-1">${item.litems[0].item_name}</span>
        <span class="py-4 fs-2 pe-5"><i class="bi bi-currency-dollar"></i>${item.litems[0].item_price}</span>
        <!-- <i class="bi bi-currency-dollar"></i> -->
    </div>
    <div class="my-3 ">
        <button class=" btn btn-info text-white border-3 border-dark fs-3 addCart"><i class="bi bi-cart-fill "></i><br>　加入　</button>
        <button class=" btn btn-secondary text-white border-3 border-dark fs-3" id="rentfile"><i class="bi bi-file-earmark-person-fill "></i><br>　查看　</button>
        <button class="btn btn-secondary text-white border-3 border-dark fs-3" id="contactposter"><i class="bi bi-chat-right-dots-fill "></i><br>　聯絡　</button>
        <a href="./calender.html" class="btn btn-primary fs-3"><i class="bi bi-calendar-range-fill "></i><br>　日期　</a>
    </div>
    <div class="my-4">
        <p class="h3">${item.litems[0].item_description}</p>
    </div>
    <div class="row my-3">
        <div class="col-6">
            <p class="h3">門市</p>
            <p class="h3">${item.member.seven11_address}</p>
            <p class="h3">${item.member.ok_address}</p>
            <p class="h3">${item.member.family_address}</p>
        </div>
        <div class="col-6">
        </div>
    </div>
</div>
</div>
<div class="col-2">

</div>`

    $(".productDetail").append(template2);
    //加到購物車
    $(".addCart").click(function () {
        console.log(item.litems[0].item_id);
        var items_id = item.litems[0].item_id;
        var p = $.ajax({
            type: "post",
            url: "/addInCart",
            data: {
                items_id: items_id
            }
        })
        $.toast({
            heading: '通知',
            text: "加入購物車",
            icon: 'info',
            loader: true,       
            loaderBg: '#9EC600' ,
            showHideTransition: 'fade',
            position:"top-center",
        })
    })

}

$(function(){

    var item_id = localStorage.getItem("item_id");
        console.log(item_id);
        downloadAndRefreshUI(item_id)
        // console.log(item)
        





    poster = getproduct(item_id)["responseJSON"]["member"]["member_id"]
    console.log("poster: "+poster)
    viewer = AjaxgetRegister()["responseJSON"]["member_id"]
    console.log("viewer: "+viewer)
    if(poster==viewer){
        x=document.getElementById('contactposter')
        x.hidden = true
        console.log(x)
    }
    $("#contactposter").click(function () {
        console.log(poster+" "+viewer)
        if(viewer!=0){
        var p = creatchatroom(poster,viewer)
        console.log(p)
        // window.location.href="chat.html";
        window.open("chat.html");
    }else{
        $.toast({
            heading: '通知',
            text: "請先登入",
            icon: 'error',
            loader: true,       
            loaderBg: '#9EC600' ,
            showHideTransition: 'fade',
            position:"top-center",
        })
    }
        })
    $("#rentfile").click(function () {
        //account
        poster = getproduct(item_id)["responseJSON"]["member"]["member_account"]
        console.log(poster)
        window.open("rentfile.html?account="+poster);
    })
    
})