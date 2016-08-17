(function() {
    'use strict';

    angular
        .module('web')
        .controller('SaleInputController', SaleInputController);

    /** @ngInject */
    function SaleInputController($scope, $uibModalInstance, $uibModal, SaleService, SERVER_PATH, $http) {

        $scope.listFinishedGoods = [];

        $scope.opts = {
            singleDatePicker: true,
            locale: { format: 'DD/MM/YY' }
        };

        $scope.onSelect = function(a, b, c, d) {
            $scope.listFinishedGoods.push({
                code: a.code,
                name: a.name,
                salePrice: a.salePrice,
                quantity: 1,
                discountPercentage: null,
                discountPrice: null
            });
            $scope.customSelected = undefined;
            updateTotalPrice();
        }

        $scope.totalPrice = 0;

        $scope.cash = {
            account: null,
            amount: null
        };

        $scope.bank = {
            account: null,
            amount: null
        };

        $scope.accountReceivable = {
            account: null,
            paymentDate: null
        };

        $scope.accountReceivable.paymentDate = {
            startDate: moment().add(30, 'days')
        };

        function remove(index) {
            $scope.listFinishedGoods.splice(index, 1);
            updateTotalPrice();
        };

        function updateTotalPrice() {
            $scope.totalPrice = 0;
            for (var i = 0; i < $scope.listFinishedGoods.length; i++) {
                $scope.totalPrice += getTotalPricePerProduct(i);
            }

            valueChange();
        }

        function getTotalPricePerProduct(i) {
            var fg = $scope.listFinishedGoods[i];

            var disc1 = 0;
            if (fg.discountPercentage !== undefined) {
                disc1 = fg.salePrice * fg.discountPercentage / 100;
            }

            var disc2 = 0;
            if (fg.discountPrice !== undefined) {
                disc2 = fg.discountPrice;
            }
            return ((fg.salePrice - disc1) * fg.quantity) - disc2;
        }

        function valueChange() {

            var cashAmount = 0;
            if ($scope.cash.amount) {
                cashAmount = $scope.cash.amount;
            }

            var bankAmount = 0;
            if ($scope.bank.amount) {
                bankAmount = $scope.bank.amount;
            }

            $scope.accountReceivable.amount = $scope.totalPrice - (parseInt(cashAmount) + parseInt(bankAmount));
        }

        function create() {

            var listFinishedGoods = [];

            if ($scope.listFinishedGoods.length == 0) {
                alert('harus ada benda yang dijual');
                return;
            }

            if ((!$scope.cash.account && $scope.cash.amount) || ($scope.cash.account && !$scope.cash.amount)) {
                alert('pilih kas dan input dp untuk kas');
                return;
            }

            if ((!$scope.bank.account && $scope.bank.amount) || ($scope.bank.account && !$scope.bank.amount)) {
                alert('pilih bank dan input dp untuk bank');
                return;
            }

            if (!$scope.accountReceivable.account && $scope.accountReceivable.amount > 0) {
                alert('pilih nama customer');
                return;
            }

            if (!$scope.accountReceivable.paymentDate && $scope.accountReceivable.amount > 0) {
                alert('input tanggal pembayaran');
                return;
            }

            if ($scope.accountReceivable.amount < 0) {
                alert('piutang harus positif');
                return;
            }

            var cashCode = null;
            if ($scope.cash.account) {
                cashCode = $scope.cash.account.code;
            }

            var bankCode = null;
            if ($scope.bank.account) {
                bankCode = $scope.bank.account.code;
            }

            var accountReceivableCode = null;
            if ($scope.accountReceivable.account) {
                accountReceivableCode = $scope.accountReceivable.account.code;
            }

            for (var i = 0; i < $scope.listFinishedGoods.length; i++) {
                var fg = $scope.listFinishedGoods[i];

                var dp1 = 0;
                if (fg.discountPrice) {
                    dp1 = fg.discountPrice;
                }

                var dp2 = 0;
                if (fg.discountPercentage) {
                    dp2 = fg.discountPercentage;
                }

                listFinishedGoods.push({
                    code: fg.code,
                    quantity: parseInt(fg.quantity),
                    discountPrice: parseFloat(dp1),
                    discountPercentage: parseInt(dp2)
                });
            }

            var cashAmount = 0;
            if ($scope.cash.amount) {
                cashAmount = $scope.cash.amount;
            }

            var bankAmount = 0;
            if ($scope.bank.amount) {
                bankAmount = $scope.bank.amount;
            }

            var obj = {
                extraDescription: $scope.faktur,
                listFinishedGoods: listFinishedGoods,
                cash: {
                    amount: parseInt(cashAmount),
                    code: cashCode
                },
                bank: {
                    amount: parseInt(bankAmount),
                    code: bankCode
                },
                accountReceivable: {
                    code: accountReceivableCode,
                    paymentDate: moment($scope.accountReceivable.paymentDate).format("DD-MM-YYYY")
                }
            };

            SaleService.save(obj, close);

        }

        $scope.getFinishGoods = function(name) {
            return $http.get(SERVER_PATH + '/finishedgoods', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });

        }


        $scope.getCash = function(name) {
            return $http.get(SERVER_PATH + '/catalogcash', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });
        }

        $scope.getBank = function(name) {
            return $http.get(SERVER_PATH + '/catalogbank', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });
        }

        $scope.getCustomer = function(name) {
            return $http.get(SERVER_PATH + '/customer', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });
        }


        $scope.remove = remove;
        $scope.create = create;
        $scope.valueChange = valueChange;
        $scope.updateTotalPrice = updateTotalPrice;
        $scope.getTotalPricePerProduct = getTotalPricePerProduct;

        $scope.cancel = cancel;

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }

})();
