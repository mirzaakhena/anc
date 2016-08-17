(function() {
    'use strict';

    angular
        .module('web')
        .controller('PurchaseInputController', PurchaseInputController);

    /** @ngInject */
    function PurchaseInputController($scope, $uibModalInstance, $uibModal, PurchaseService, SERVER_PATH, $http) {

        $scope.listRawMaterial = [];

        $scope.opts = {
            singleDatePicker: true,
            locale: { format: 'DD/MM/YY' }
        };

        $scope.onSelect = function(a, b, c, d) {
            $scope.listRawMaterial.push({
                code: a.code,
                name: a.name,
                price: a.price,
                quantity: 1
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

        $scope.accountPayable = {
            account: null,
            paymentDate: null
        };

        $scope.accountPayable.paymentDate = {
            startDate: moment().add(30, 'days')
        };

        function remove(index) {
            $scope.listRawMaterial.splice(index, 1);
            updateTotalPrice();
        };

        function updateTotalPrice() {
            $scope.totalPrice = 0;
            for (var i = 0; i < $scope.listRawMaterial.length; i++) {
                $scope.totalPrice += getTotalPricePerProduct(i);
            }

            valueChange();
        }

        function getTotalPricePerProduct(i) {
            var fg = $scope.listRawMaterial[i];
            return fg.price * fg.quantity;
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

            $scope.accountPayable.amount = $scope.totalPrice - (parseInt(cashAmount) + parseInt(bankAmount));
        }

        function create() {

            var listRawMaterial = [];

            if ($scope.listRawMaterial.length == 0) {
                alert('harus ada benda yang dibeli');
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

            if (!$scope.accountPayable.account && $scope.accountPayable.amount > 0) {
                alert('pilih nama supplier');
                return;
            }

            if (!$scope.accountPayable.paymentDate && $scope.accountPayable.amount > 0) {
                alert('input tanggal pembayaran');
                return;
            }

            if ($scope.accountPayable.amount < 0) {
                alert('utang harus positif');
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

            var accountPayableCode = null;
            if ($scope.accountPayable.account) {
                accountPayableCode = $scope.accountPayable.account.code;
            }

            for (var i = 0; i < $scope.listRawMaterial.length; i++) {
                var fg = $scope.listRawMaterial[i];

                listRawMaterial.push({
                    code: fg.code,
                    price: fg.price,
                    quantity: parseInt(fg.quantity)
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
                listRawMaterial: listRawMaterial,
                cash: {
                    amount: parseInt(cashAmount),
                    code: cashCode
                },
                bank: {
                    amount: parseInt(bankAmount),
                    code: bankCode
                },
                accountPayable: {
                    code: accountPayableCode,
                    paymentDate: moment($scope.accountPayable.paymentDate).format("DD-MM-YYYY")
                }
            };

            PurchaseService.save(obj, close);

        }

        $scope.getRawMaterial = function(name) {
            return $http.get(SERVER_PATH + '/rawmaterial', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });

        }

        $scope.getCash = function (name) {
            return $http.get(SERVER_PATH + '/catalogcash', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });
        }

        $scope.getBank=function (name) {
            return $http.get(SERVER_PATH + '/catalogbank', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });
        }

        $scope.getSupplier=function(name) {
            return $http.get(SERVER_PATH + '/supplier', {
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
