package manitto.backend.global.util;

import java.util.List;
import java.util.Random;

public class PasswordProvider {

    private static final Random random = new Random();

    private static final List<String> ANIMALS = List.of(
            "수달", "산양", "반달가슴곰", "삵", "저어새", "따오기", "황새", "붉은박쥐", "독수리", "검은머리갈매기", "표범장지뱀", "가시고기", "긴꼬리딱새", "금개구리",
            "두꺼비", "붉은배새매", "참매", "검독수리", "양비둘기", "검은머리촉새", "호사비오리", "노랑부리저어새", "알락꼬리마도요", "흰목물떼새", "새홀리기", "검은머리딱새",
            "붉은점모시나비", "왕은점표범나비", "참호박벌", "날개띄기민물장어", "꼬치동자개", "팔색조", "따개비고둥", "큰주홍부전나비", "주름날개멸치", "자주방게", "장수하늘소",
            "황금박쥐", "백조", "붉은여우", "늑대", "마라도딱새", "울릉도독도박쥐", "멧돼지", "산토끼", "노루", "하늘다람쥐", "산고양이", "사막여우", "남방큰돌고래",
            "바다사자", "얼룩말", "붉은원숭이", "산양", "아무르호랑이", "백두산호랑이", "사파리동자개", "백두산곰", "시베리아산양", "하늘거북", "지리산수달", "물범", "북극곰",
            "회색곰", "애기불가사리", "회색돌고래", "검은고래", "바다거북", "산호초물고기", "호주머니쥐", "금강산원숭이", "제주도박쥐", "회색여우", "아프리카코끼리", "아시아코끼리"
    );

    private static final List<String> COLORS = List.of(
            "빨강", "하양", "파란", "검은", "주황", "노랑", "푸른", "초록", "보라", "남색", "분홍"
    );

    private static String getRandomAnimal() {
        return ANIMALS.get(random.nextInt(ANIMALS.size()));
    }

    private static String getRandomColor() {
        return COLORS.get(random.nextInt(COLORS.size()));
    }

    public static String generatePassword() {
        return getRandomColor() + getRandomAnimal();
    }
}
