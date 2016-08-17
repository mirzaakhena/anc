(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {
        // $stateProvider
        //   .state('home', {
        //     url: '/',
        //     templateUrl: 'app/main/main.html',
        //     controller: 'MainController',
        //     controllerAs: 'main'
        //   });        
        // $urlRouterProvider.otherwise('/');

        // $stateProvider
        //     .state('app', {
        //         abstract: true,
        //         templateUrl: 'app/cucian/layout/basic-layout.html'
        //     });

        // $urlRouterProvider.otherwise('/cucian/masuk');


        $stateProvider
            .state('app', {
                abstract: true,
                templateUrl: 'app/accounting/navigation.html',
                controller:  'NavigationController',
                controllerAs: 'nav'
            })

        $urlRouterProvider.otherwise('/journal');

        // $stateProvider
        //     .state('app', {
        //         url:'/mainmenu',
        //         templateUrl: 'app/accounting/mainmenu.html',
        //         controller: 'MainMenuController',
        //     })

        // $urlRouterProvider.otherwise('/mainmenu');


        // .state('login', {
        //     url:'/login',
        //     templateUrl: 'app/accounting/login/login.html',
        //     controller:  'LoginController'
        // })
        // .state('logout', {
        //     url:'/logout',
        //     templateUrl: 'app/accounting/logout/logout.html',
        //     controller:  'LogoutController'
        // })
        // .state('register', {
        //     url:'/register',
        //     templateUrl: 'app/accounting/register/register.html',
        //     controller:  'RegisterController'
        // })
        // .state('role', {
        //     url:'/role',
        //     templateUrl: 'app/accounting/role/role.html',
        //     controller:  'RoleController'
        // });            



    }

})();
