package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.mapper")
public class MusicServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(MusicServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MusicServerApplication.class, args);
        logger.info("启动成功");
        logger.info("\n      　┏┓　　　┏┓+ +\n" +
                "     　┏┛┻━━━┛┻┓ + +\n" +
                "     　┃　　　　　　　┃ 　\n" +
                "     　┃　　　━　　　┃ ++ + + +\n" +
                "      ████━████ ┃+\n" +
                "     　┃　　　　　　　┃ +\n" +
                "     　┃　　　┻　　　┃\n" +
                "     　┃　　　　　　　┃ + +\n" +
                "     　┗━┓　　　┏━┛\n" +
                "     　　　┃　　　┃　　　　　　　　　　　\n" +
                "     　　　┃　　　┃ + + + +\n" +
                "     　　　┃　　　┃\n" +
                "     　　　┃　　　┃ +  神兽保佑\n" +
                "     　　　┃　　　┃    代码无bug　　\n" +
                "     　　　┃　　　┃　　+　　　　　　　　　\n" +
                "     　　　┃　 　　┗━━━┓ + +\n" +
                "     　　　┃ 　　　　　　　┣┓\n" +
                "     　　　┃ 　　　　　　　┏┛\n" +
                "     　　　┗┓┓┏━┳┓┏┛ + + + +\n" +
                "     　　　　┃┫┫　┃┫┫\n" +
                "     　　　　┗┻┛　┗┻┛+ + + +");
        logger.info("\n"+
                "按下你想按的\n"+
                "  ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐\n" +
                "  │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐\n" +
                "  └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘\n" +
                "  ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐\n" +
                "  │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │\n" +
                "  ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤\n" +
                "  │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \\ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │\n" +
                "  ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │\n" +
                "  │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│\" '│ Enter  │               │ 4 │ 5 │ 6 │   │\n" +
                "  ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤\n" +
                "  │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │\n" +
                "  ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││\n" +
                "  │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│\n" +
                "  └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘");
    }




}
