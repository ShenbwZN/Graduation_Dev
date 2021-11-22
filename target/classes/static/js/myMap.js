function loadMainMap() {
    $.ajax({
        url: "/stations",
        type: "post",
        dataType: "json",
        success: function (data) {
            loadMap(data);
        },
        error: function () {
            alert("数据加载失败");
        }
    })
}

function loadMap(data) {
    const map = new AMap.Map('container_map', {
        resizeEnable: true, //是否监控地图容器尺寸变化
        zoom: 9, //初始化地图层级
        center: [114.477822, 38.133184], //初始化地图中心点
        layers: [new AMap.TileLayer.Satellite()],
        pitch: 30, // 地图俯仰角度，有效范围 0 度- 83 度
        viewMode: '3D'
    });
    // 渲染数据
    loadMarker(map, data);

    // 行政区划
    drawDistrict(map);
}

function loadMarker(map, data) {
    const infoWindow = new AMap.InfoWindow({
        // isCustom:true, // 这个打开，是要自己设置显示格式的
        offset: new AMap.Pixel(-10, -31)
    });
    map.clearMap();
    // map.setLayers([layerSatellite]);
    // 点的点击事件
    const markerClick = function (e) {
        infoWindow.setContent(e.target.content);
        infoWindow.open(map, e.target.getPosition());
    };

    // 循环创建所有点
    for (let i = 0; i < data.length; i++) {
        const marker = new AMap.Marker({
            map: map,
            position: [data[i].stationLon, data[i].stationLat],
            title: data[i].stationName,
            offset: new AMap.Pixel(-10, -31),
        });

        // 设置点的内容
        const content = [];
        content.push("监测站：" + data[i].stationName);
        content.push("监测站编码：" + data[i].stationCode);
        content.push("监测站位置：" + data[i].stationLocation);
        // target='_blank' 新页面打开
        content.push("<a href='details?staCode=" + data[i].stationCode + "'>详情</a>");

        // 设置点的点击显示内容
        marker.content = content.join("<br/>");
        marker.on('click', markerClick);
        // map.add(marker);
    }
}

// 行政区划分，即边界
function drawDistrict(map) {
    // 行政区划设置
    const opts = {
        // 1-返回下一级行政区；0-不需要
        subdistrict: 0,
        // 返回行政区边界坐标等具体信息
        extensions: 'all',
        // 设置查询行政区级别，district，city,province
        level: 'district',
    };

    // 创建行政区对象
    const district = new AMap.DistrictSearch(opts);
    district.search("石家庄市", function (status, result) {
        // 获取石家庄的边界信息
        const bounds = result.districtList[0].boundaries;
        const polygons = [];

        const outer = [
            new AMap.LngLat(-360, 90, true),
            new AMap.LngLat(-360, -90, true),
            new AMap.LngLat(360, -90, true),
            new AMap.LngLat(360, 90, true),
        ];
        const pathArray = [outer];
        pathArray.push.apply(pathArray, bounds);
        if (bounds) {
            for (let i = 0, l = bounds.length; i < l; i++) {
                //生成行政区划polygon
                var polygon = new AMap.Polygon({
                    path: pathArray,
                    strokeWeight: 1,
                    // path: bounds[i],
                    fillOpacity: 0.9,
                    fillColor: '#000000',
                });
                polygons.push(polygon);
            }
            // 地图自适应
            // map.setFitView();
        }
        map.add(polygons);
    })
}
