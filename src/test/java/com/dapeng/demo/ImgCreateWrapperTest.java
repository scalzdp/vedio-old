package com.dapeng.demo;


//import com.github.hui.quick.plugin.base.Base64Util;
//import com.github.hui.quick.plugin.base.FileReadUtil;
//import com.github.hui.quick.plugin.base.GraphicUtil;
//import com.github.hui.quick.plugin.base.ImageLoadUtil;
//import com.github.hui.quick.plugin.image.wrapper.create.ImgCreateOptions;
//import com.github.hui.quick.plugin.image.wrapper.create.ImgCreateWrapper;
//import com.github.hui.quick.plugin.image.util.FontUtil;
import com.dapeng.demo.base.Base64Util;
import com.dapeng.demo.base.FileReadUtil;
import com.dapeng.demo.base.ImageLoadUtil;
import com.dapeng.demo.utils.FontUtil;
import com.dapeng.demo.wrapper.create.ImgCreateOptions;
import com.dapeng.demo.wrapper.create.ImgCreateWrapper;
import com.sun.imageio.plugins.common.ImageUtil;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yihui on 2017/8/17.
 */
public class ImgCreateWrapperTest {

    @Test
    public void testGenImg() throws IOException {
        int w = 400;
        int leftPadding = 10;
        int topPadding = 40;
        int bottomPadding = 40;
        int linePadding = 10;
        Font font = new Font("宋体", Font.PLAIN, 18);

        ImgCreateWrapper.Builder build = ImgCreateWrapper.build()
                .setImgW(w)
                .setLeftPadding(leftPadding)
                .setTopPadding(topPadding)
                .setBottomPadding(bottomPadding)
                .setLinePadding(linePadding)
                .setFont(font)
                .setAlignStyle(ImgCreateOptions.AlignStyle.CENTER)
                .setDrawStyle(ImgCreateOptions.DrawStyle.HORIZONTAL)
//                .setBgImg(ImageUtil.getImageByPath("createImg/bg.jpeg"))
                .setBgColor(Color.WHITE)
                .setBorder(true)
                .setBorderColor(0xFFF7EED6);


        BufferedReader reader = FileReadUtil.createLineRead("text/poem.txt");
        String line;
        int index = 0;
        while ((line = reader.readLine()) != null) {
            build.drawContent(line);

            if (++index == 5) {
                build.drawImage(ImageLoadUtil.getImageByPath("https://static.oschina.net/uploads/img/201708/12175633_sOfz.png"));
            }

            if (index == 7) {
                build.setFontSize(25);
            }

            if (index == 10) {
                build.setFontSize(20);
                build.setFontColor(Color.RED);
            }
        }

        BufferedImage img = build.asImage();
        String out = Base64Util.encode(img, "png");
        System.out.println("<img src=\"data:image/png;base64," + out + "\" />");
    }


    @Test
    public void testLocalGenImg() throws IOException {
        int w = 400;
        int leftPadding = 10;
        int topPadding = 20;
        int bottomPadding = 10;
        int linePadding = 10;
        Font font = new Font("手札体", Font.PLAIN, 18);

        ImgCreateWrapper.Builder build = ImgCreateWrapper.build()
                .setImgW(w)
                .setLeftPadding(leftPadding)
                .setRightPadding(leftPadding)
                .setTopPadding(topPadding)
                .setBottomPadding(bottomPadding)
                .setLinePadding(linePadding)
                .setFont(font)
                .setAlignStyle(ImgCreateOptions.AlignStyle.CENTER)
                .setDrawStyle(ImgCreateOptions.DrawStyle.HORIZONTAL)
                .setBgColor(Color.WHITE)
                .setBorder(true)
                .setBorderColor(0xFFF7EED6);


        BufferedReader reader = FileReadUtil.createLineRead("text/poem.txt");
        String line;
        while ((line = reader.readLine()) != null) {
            build.drawContent(line);
        }

        build.setAlignStyle(ImgCreateOptions.AlignStyle.RIGHT)
//                .drawImage("https://gitee.com/liuyueyi/Source/raw/master/img/info/blogInfoV2.png");
                    .drawImage("D:\\background.png");

        BufferedImage img = build.asImage();
        ImageIO.write(img, "png", new File("D:\\2out.png"));
    }

    @Test
    public void testMutiPng() throws IOException {
        int w = 400;
        int leftPadding = 10;
        int topPadding = 20;
        int bottomPadding = 10;
        int linePadding = 10;
        Font font = new Font("手札体", Font.PLAIN, 18);



        BufferedReader reader = FileReadUtil.createLineRead("text/poem.txt");
        String line;
        List<String> content = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            content.add(line);
        }
        List<List<String>> sprateList = ListUtils.partition(content,10);
        sprateList.forEach(c->{

            ImgCreateWrapper.Builder build = ImgCreateWrapper.build()
                    .setImgW(w)
                    .setLeftPadding(leftPadding)
                    .setRightPadding(leftPadding)
                    .setTopPadding(topPadding)
                    .setBottomPadding(bottomPadding)
                    .setLinePadding(linePadding)
                    .setFont(font)
                    .setAlignStyle(ImgCreateOptions.AlignStyle.CENTER)
                    .setDrawStyle(ImgCreateOptions.DrawStyle.HORIZONTAL)
                    .setBgColor(Color.WHITE)
                    .setBorder(true)
                    .setBorderColor(0xFFF7EED6);
            c.forEach(b->build.drawContent(b));

            build.setAlignStyle(ImgCreateOptions.AlignStyle.RIGHT).drawImage("D:\\background.png");
            BufferedImage img = build.asImage();
            try {
                ImageIO.write(img, "png", new File("D:\\files\\"+countTextNum(c)+"out.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Integer countTextNum(List<String> c){
        return c.stream().map(s->s.length()).reduce(0,Integer::sum);
    }


    @Test
    public void testLocalGenVerticalImg() throws IOException, FontFormatException {
        int h = 400;
        int leftPadding = 10;
        int topPadding = 10;
        int bottomPadding = 10;
        int linePadding = 10;
        Font font = FontUtil.getFont("font/txlove.ttf", Font.PLAIN, 20);

        ImgCreateWrapper.Builder build = ImgCreateWrapper.build()
                .setImgH(h)
                .setDrawStyle(ImgCreateOptions.DrawStyle.VERTICAL_RIGHT)
                .setAlignStyle(ImgCreateOptions.AlignStyle.CENTER)
                .setLeftPadding(leftPadding)
                .setTopPadding(topPadding)
                .setBottomPadding(bottomPadding)
                .setLinePadding(linePadding)
                .setFont(font)
                .setBgColor(Color.WHITE)
                .setBorder(true)
                .setBorderBottomPadding(8)
                .setBorderLeftPadding(6)
                .setBorderTopPadding(8)
                .setBorderColor(0xFFF7EED6);


        BufferedReader reader = FileReadUtil.createLineRead("text/poem.txt");
        String line;
        while ((line = reader.readLine()) != null) {
            build.drawContent(line);
        }

        build.setFont(FontUtil.getFont("font/txlove.ttf", Font.ITALIC, 18))
                .setAlignStyle(ImgCreateOptions.AlignStyle.BOTTOM);
        build.drawContent(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        build.drawContent(" ");
        build.setAlignStyle(ImgCreateOptions.AlignStyle.CENTER)
                .drawImage("https://gitee.com/liuyueyi/Source/raw/master/img/info/blogInfoV2.png");
//        build.setFontColor(Color.BLUE).drawContent("后缀签名").drawContent("灰灰自动生成");

        BufferedImage img = build.asImage();
        ImageIO.write(img, "png", new File("/tmp/2out.png"));
    }
}
