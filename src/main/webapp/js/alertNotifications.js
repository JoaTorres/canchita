function alertGrowl(message, type) {
    $.bootstrapGrowl(message, {type: type, delay: 3000, offset: {from: 'top', amount: 60}, align: 'center', width: 300});
};

function alertTop(message, type) {
    $.bootstrapGrowl(message, {type: type, delay: 1000, offset: {from: 'top', amount: 50}, align: 'right', width: 300});
};

function alertTopLeft(message, type) {
    $.bootstrapGrowl(message, {type: type, delay: 1000, offset: {from: 'top', amount: 60}, align: 'left', width: 300});
};

function alertBottom(message, type) {
    $.bootstrapGrowl(message, {type: type, delay: 1000, offset: {from: 'bottom', amount: 60}, align: 'left', width: 300});
};

function alert(message, type, width) {
    $.bootstrapGrowl(message, {type: type, delay: 3000, offset: {from: 'top', amount: 60}, align: 'center', width: width});
};

