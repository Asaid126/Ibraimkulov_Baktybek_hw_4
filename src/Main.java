import java.util.Random;

public class Main {
    public static int bossHealth = 900;//здоровье босса
    public static int bossDamage = 50;//урон который наносит босс
    public static String bossDefence;//защита босса
    public static int[] heroesHealth = {270, 260, 250, 200, 150, 220, 300, 280};//глобальный массив здоровья *M
    public static int[] heroesDamage = {10, 15, 20, 10, 20, 15, 5, 0};//глобальный массив УРОНА ГГ
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Lucky", "Thor", "Berserk", "Golem", "Medic"};//массив списка героев
    public static int roundNumber = 0;//глобальная переменная номера раунда
    public static int help = 30; //*M
    public static boolean stop; //*th
    public static boolean DontProtect; //Gol
    public static int protectBers; //*B


    // ГЛАВНЫЙ МЕТОД!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static void main(String[] args) {
        printStatistics(); // метод 1 СТАТИСТИКА НА ДАННЫЙ МОМЕН

        while (!isGameFinished()) { //МЕТОД 2 (В.)
            playRound();// МЕТОД 3 (Н.В)
        }
    }

    //МЕТОД 1(невозвращаемый) СТАТИСТИКА НА ДАННЫЙ МОМЕН распечатывает перед игрой номер раунда и состояние игроков
    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " -------------");
        System.out.println("Boss health: " + bossHealth + " damage: " +
                bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }

    //МЕТОД 2(возвращаемый)  Останавливает игру(возвращая true) и распечатывает кто победил
    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    //  МЕТОД 3(невозвращаемый) вся возня здесь,своего рода второй главный метод
    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        protectGolem();
        heroesHit();
        printStatistics();
        MedikHelp();
    }

    //методик 1 выбор защиты для босса(рандомно выбирается индекс одного из героев) защите присваивается тип героя
    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }


    // методик 2 . отнимает значение удара босса от жизней ГГ
    public static void bossHits() {
        Random random = new Random();
        for (int i = 0; i < heroesHealth.length && !stop; i++) {
            boolean randomLuck = random.nextBoolean();                     // L
            if (randomLuck && i == 3) {                                    // u
                System.out.println("lucky ты счасливчик");                 // c
                DontProtect = true;                                        // k
                continue;                                                  // y
            }                                                              // !
            if (i == 5) {                                       //b
                protectBers = bossDamage / 2;                   //b
            }                                                   //b
            else {                                              //b
                protectBers = 0;                                //b
            }                                                   //b
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - (bossDamage - protectBers);
                }
            }
        }
    }

    // методик 3 отнимает значение ударов ГГ от жизни Босса
    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
            }
            int damage = heroesDamage[i];
            if (heroesAttackType[i] == bossDefence) {
                Random random = new Random();
                int coeff = random.nextInt(9) + 2; //2,3,4,5,6,7,8,9,10
                damage = heroesDamage[i] * coeff;
                System.out.println("Critical damage: " + damage);
            }
            Random random = new Random();                                  //th
            boolean randomThor = random.nextBoolean();                     //th
            if (i == 4 && heroesHealth[4] > 0) {                           //th
                if (randomThor) {                                          //th
                    stop = true;                                           //th
                    System.out.println("Босс оглушен и не сможет атаковать в раунде " + roundNumber);
                    System.out.println("(и в раунде " + (roundNumber + 1) + " уровень health ГГ не изменится)");    //th
                }                                                          //th
                else {                                                     //th
                    stop = false;                                          //th
                }                                                          //th
            }                                                              //th
            if (i == 5) {                                       //b
                protectBers = bossDamage / 2;                   //b
            }                                                   //b
            else {                                              //b
                protectBers = 0;                                //b
            }
            if (bossHealth - damage < 0) {
                bossHealth = 0;
            } else {
                bossHealth = bossHealth - (damage + protectBers);
            }
        }
    }


    public static void MedikHelp() {
        if (heroesHealth[heroesHealth.length - 1] > 0) {
            for (int i = 0; i < heroesHealth.length - 1; i++) {
                if (heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                    for (int a = 0; a < heroesHealth.length - 1; a++) {
                        int min;
                        if (heroesHealth[a] < heroesHealth[i] && heroesHealth[a] > 0) {
                            i = a;
                        }
                    }
                    System.out.println("Help " + heroesAttackType[i] + "+" + help + " Health");
                    heroesHealth[i] += help;
                    break;
                }
            }
        }
    }


    public static void protectGolem() {
        if (heroesHealth[6] > 0 && !stop) {
            System.out.println("Golem вас прикрывает");
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i == 6)
                    continue;
                if (i == 3 && DontProtect)
                    continue;
                if (heroesHealth[i] > 0 && !stop) {
                    heroesHealth[i] += bossDamage / 5;
                    heroesHealth[6] -= bossDamage / 5;
                    if (heroesHealth[6] < 0) {
                        heroesHealth[6] = 0;
                    }

                }
            }
        }
    }
}




