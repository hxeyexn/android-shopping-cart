package woowacourse.shopping.database

val firstJsonProducts = """
    [
        {
            "id": 0,
            "name": "돌체 콜드 브루",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002081]_20210415133656839.jpg",
            "price": 5000
        },
        {
            "id": 1,
            "name": "민트 콜드 브루",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/10/[9200000004312]_20221005145029134.jpg",
            "price": 5000
        },
        {
            "id": 2,
            "name": "에스프레소 마키아또",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[25]_20210415144211211.jpg",
            "price": 5500
        },
        {
            "id": 3,
            "name": "카라멜 마키아또",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[126197]_20210415154609863.jpg",
            "price": 6500
        },
        {
            "id": 4,
            "name": "라벤더 카페 브레베",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/04/[9200000004119]_20220412083025862.png",
            "price": 5000
        },
        {
            "id": 5,
            "name": "아이스 더 그린 쑥 크림 라떼",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/02/[9200000004529]_20230206091908618.jpg",
            "price": 4500
        },
        {
            "id": 6,
            "name": "아이스 화이트 초콜릿 모카",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110572]_20210415155545375.jpg",
            "price": 5000
        },
        {
            "id": 7,
            "name": "클래식 민트 모카",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/10/[9200000004313]_20221005145156959.jpg",
            "price": 5000
        },
        {
            "id": 8,
            "name": "바닐라 플랫 화이트",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002406]_20210415135507733.jpg",
            "price": 6000
        },
        {
            "id": 9,
            "name": "바닐라 아포가토",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/09/[9200000004308]_20220916101121079.jpg",
            "price": 5000
        },
        {
            "id": 10,
            "name": "리얼 블루베리 베이글",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/03/[9300000003334]_20210310092057351.jpg",
            "price": 5000
        },
        {
            "id": 11,
            "name": "리얼 치즈 베이글",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/03/[9300000003335]_20210310092146175.jpg",
            "price": 5000
        },
        {
            "id": 12,
            "name": "바질 토마토 크림치즈 베이글",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/03/[9300000003223]_20210315170846073.jpg",
            "price": 5000
        },
        {
            "id": 13,
            "name": "미니 클래식 스콘",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[5110001099]_20210421161145644.jpg",
            "price": 5000
        },
        {
            "id": 14,
            "name": "피넛 쑥 떡 스콘",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/03/[9300000004028]_20220314152820975.jpg",
            "price": 5000
        },
        {
            "id": 15,
            "name": "거문 오름 크루아상",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000001361]_20210421133918737.jpg",
            "price": 7000
        },
        {
            "id": 16,
            "name": "너티 크루아상",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/01/[9300000004372]_20230102083042772.jpg",
            "price": 5000
        },
        {
            "id": 17,
            "name": "매콤 소시지 불고기 베이크",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000002227]_20210421160744685.jpg",
            "price": 5000
        },
        {
            "id": 18,
            "name": "미니 리프 파이",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/02/[9300000004008]_20220218143920309.jpg",
            "price": 5000
        },
        {
            "id": 19,
            "name": "뺑 오 쇼콜라",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000002431]_20210421164613125.jpg",
            "price": 5000
        }
    ]
""".trimIndent()

val secondJsonProducts = """
    [
        {
            "id": 20,
            "name": "스모크드 소시지 브레드",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000002445]_20210421172107585.jpg",
            "price": 5000
        },
        {
            "id": 21,
            "name": "연유 밀크모닝",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9300000003175]_20210210174347318.jpg",
            "price": 5000
        },
        {
            "id": 22,
            "name": "오름 치즈 케이츄리",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/07/[9300000003520]_20210727081330163.jpg",
            "price": 5000
        },
        {
            "id": 23,
            "name": "올래 미니 크루아상",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2020/06/[9300000002848]_20200626143224628.jpg",
            "price": 5000
        },
        {
            "id": 24,
            "name": "주상절리 파이",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000002489]_20210421134243043.jpg",
            "price": 5000
        },
        {
            "id": 25,
            "name": "크림치즈 브리오슈 보스톡",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/03/[9300000002931]_20210325161934333.jpg",
            "price": 5000
        },
        {
            "id": 26,
            "name": "23 서머 그린 머그 355ml",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/04/[9300000004357]_20230428094403109.jpg",
            "price": 15000
        },
        {
            "id": 27,
            "name": "바리스타춘식 캐릭터 머그",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/01/[9300000004332]_20230130132448085.jpg",
            "price": 30000
        },
        {
            "id": 28,
            "name": "리저브 골드 테일 머그",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/08/[11123299]_20210804102144453.jpg",
            "price": 25000
        },
        {
            "id": 29,
            "name": "스타벅스 브런치 세트",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/08/[9300000004214]_20220822132741879.jpg",
            "price": 60000
        },
        {
            "id": 30,
            "name": "제주 한라봉 머그 237ml",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/03/[9300000004323]_20230307143135598.jpg",
            "price": 40000
        },
        {
            "id": 31,
            "name": "베이스볼 머그",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/05/[9300000004471]_20230510163213366.jpg",
            "price": 34000
        },
        {
            "id": 32,
            "name": "23 서머 기린 머그",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/04/[11141873]_20230428125930267.jpg",
            "price": 45000
        },
        {
            "id": 33,
            "name": "플러피 판다 핫 초콜릿",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002594]_20210422080327783.jpg",
            "price": 6000
        },
        {
            "id": 34,
            "name": "아이스 제주 까망 라떼",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2020/09/[9200000001302]_20200921171804529.jpg",
            "price": 8000
        },
        {
            "id": 35,
            "name": "우유",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[18]_20210426095514018.jpg",
            "price": 3000
        },
        {
            "id": 36,
            "name": "스타벅스 슬래머",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252131.jpg",
            "price": 6500
        },
        {
            "id": 37,
            "name": "제주 한라봉 말차 블렌디드",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/04/[9200000004565]_20230414131720416.jpg",
            "price": 7000
        },
        {
            "id": 38,
            "name": "플럼 선셋 유스베리 티",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/04/[9200000004564]_20230414131633174.jpg",
            "price": 7000
        },
        {
            "id": 39,
            "name": "말차 샷 아포가토",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/09/[9200000004305]_20220902105142976.jpg",
            "price": 5500
        }
    ]
""".trimIndent()

val thirdJsonProducts = """
    [
        {
            "id": 40,
            "name": "아이스 유자 민트 티",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/04/[9200000002959]_20220411155904911.jpg",
            "price": 4500
        },
        {
            "id": 41,
            "name": "망고 용과 레모네이드 스타벅스 리프레셔",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/04/[9200000004439]_20230413152833643.jpg",
            "price": 8000
        },
        {
            "id": 42,
            "name": "딸기 아사이 레모네이드 스타벅스 리프레셔",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/08/[9200000003763]_20220803131322551.jpg",
            "price": 8000
        },
        {
            "id": 43,
            "name": "스파클링 시트러스 에스프레소",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/03/[9200000003506]_20210322093317854.jpg",
            "price": 5000
        },
        {
            "id": 44,
            "name": "얼 그레이 바닐라 티 라떼",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/01/[9200000004285]_20230118084943128.jpg",
            "price": 4500
        },
        {
            "id": 45,
            "name": "리저브 나이트로",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000002407]_20210225095106743.jpg",
            "price": 6000
        },
        {
            "id": 46,
            "name": "레드벨벳 크림치즈 케이크",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[5110007192]_20210421153949029.jpg",
            "price": 5000
        },
        {
            "id": 47,
            "name": "당근 현무암 케이크",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000001055]_20210421133631796.jpg",
            "price": 7000
        },
        {
            "id": 48,
            "name": "슈크림 가득 바움쿠헨",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2023/01/[9300000002448]_20230102083308549.jpg",
            "price": 6000
        },
        {
            "id": 49,
            "name": "백년초콜릿 크런치 케이크",
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2020/06/[9300000002856]_20200626140039590.jpg",
            "price": 7000
        }
    ]
""".trimIndent()
