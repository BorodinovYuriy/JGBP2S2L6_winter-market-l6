angular.module('app',['ngStorage']).controller('indexController', function($scope, $http, $localStorage){

    console.log("test console.log: index.js - is working!")

//    const contextPath = 'http://localhost:8189/winter/api/v1';
//    const cartPath = 'http://localhost:8190/winter-carts/api/v1';
    $scope.pageNumber = 1;


//Authentication-----------------------------
$scope.tryToAuth = function(){
    $http.post('http://localhost:5555/auth/auth', $scope.user)
        .then(function successCallback(response){
            if(response.data.token){
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.winterMarketUser = {username: $scope.user.username, token: response.data.token};

console.log("User is authorized, token: " + $http.defaults.headers.common.Authorization)

                $scope.user.username = null;
                $scope.user.password = null;
            }
        }, function errorCallback(response){
        });
};

$scope.tryToLogout = function(){
    $scope.clearUser();
    $scope.user = null;
};

$scope.clearUser = function(){
    delete $localStorage.winterMarketUser;
    $http.defaults.headers.common.Authorization = '';
        console.log("User shutdown, [Authorization] is empty")
};

$scope.isUserLoggedIn = function(){
    if ($localStorage.winterMarketUser){
        return true;
    }else{
        return false;
    }
};

//$scope.authCheck= function(){
//    $http.get('http://localhost:5555/core/auth_check').then(function(response){
//        alert(response.data.value);
//    });
//};

if($localStorage.winterMarketUser){
    try{
        let jwt = $localStorage.winterMarketUser.token;
        let payload = JSON.parse(atob(jwt.split('.')[1]));
        let currentTime = parseInt(new Date().getTime() / 1000);
        if(currentTime > payload.exp){
            console.log("Token is expired!");
            delete $localStorage.winterMarketUser;
            $http.defaults.headers.common.Authorization ='';
            }
    }catch (e){
    }

    $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.winterMarketUser.token;
};
//Authentication-----------------------------


//Получение списка продуктов
$scope.loadProducts = function() {
                $http({
                    url: 'http://localhost:5555/core/api/v1/products',
                    method: 'GET',
                    params: {
                             p: $scope.pageNumber,
                             min_price: $scope.filter ? $scope.filter.min_price : null,
                             max_price: $scope.filter ? $scope.filter.max_price : null,
                             title_part: $scope.filter ? $scope.filter.title_part : null
                    }
                }).then(function(response) {
                                  $scope.productList = response.data.content;
                })
}
//Пагинация
$scope.change_page = function(pageVar) {
             $scope.pageNumber = $scope.pageNumber + pageVar;
             if($scope.pageNumber <= 0){
                    $scope.pageNumber = 1
             }
             $http({
                    url: 'http://localhost:5555/core/api/v1/products',
                    method: 'GET',
                    params: {
                             p: $scope.pageNumber,
                             min_price: $scope.filter ? $scope.filter.min_price : null,
                             max_price: $scope.filter ? $scope.filter.max_price : null,
                             title_part: $scope.filter ? $scope.filter.title_part : null
                    }
             }).then(function(response) {
                     $scope.productList = response.data.content;
             })
}
//Информация о продукте
$scope.showProductInfo = function(productId){
    $http.get('http://localhost:5555/core/api/v1/products/'+productId)
            .then(function(response){
            alert(response.data.title);
    })
}
//Удаление
$scope.deleteProductById = function(id){
            $http.delete('http://localhost:5555/core/api/v1/products/' + id)
            .then(function(response) {
                $scope.loadProducts();
            })
}
//Добавить в корзину
$scope.addToCart = function(productId){
    $http.get('http://localhost:5555/cart/api/v1/cart'+'/add/' + productId).then(function(response){
    $scope.loadCart();
    })
}
//Отображение корзины
$scope.loadCart = function(){
    $http.get('http://localhost:5555/cart/api/v1/cart').then(function(response){
    $scope.cart = response.data;
    })
}
//Удаление из корзины
$scope.deleteFromCart = function(productId){
    $http.delete('http://localhost:5555/cart/api/v1/cart/'+ productId).then(function(response){
    $scope.loadCart();
    })
}
//Очистить корзину
$scope.clearCart = function(productId){
    $http.delete('http://localhost:5555/cart/api/v1/cart').then(function(response){
    $scope.loadCart();
    })
}
//Количество в корзине
$scope.changeQuantity = function(productId, number){
    if(number < 0){
            $http.put('http://localhost:5555/cart/api/v1/cart' + '/decrease/' + productId)
            .then(function(response){
                $scope.loadCart();
            })
    }
    if(number > 0){
            $http.put('http://localhost:5555/cart/api/v1/cart' + '/increase/' + productId)
            .then(function(response){
                $scope.loadCart();
            })
    }

}
//Оформление заказа
$scope.createOrder = function(){
     $http.post('http://localhost:5555/core/api/v1/orders')
                .then(function(response){
                        alert('Заказ оформлен!');
                        $scope.loadCart();
                })
};



$scope.loadProducts();
$scope.loadCart();

});

