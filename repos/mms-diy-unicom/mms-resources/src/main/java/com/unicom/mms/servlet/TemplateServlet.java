package com.unicom.mms.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import magick.MagickImage;

import org.apache.commons.configuration.CompositeConfiguration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unicom.mms.base.constants.FilePathConstants;
import com.unicom.mms.draw.MagickDraw;
import com.unicom.mms.resources.UrlPathConstants;
import com.unicom.mms.resources.drawinfo.TemplateMmsInfo;
import com.unicom.mms.resources.drawinfo.TextInfo;
import com.unicom.mms.util.Config;

public class TemplateServlet extends HttpServlet {
	private static final long serialVersionUID = 8792621246913921951L;

	public TemplateMmsInfo initData(){
		TemplateMmsInfo info = new TemplateMmsInfo();
		info.setPostcard("systemData/templateCardPath/demo.jpg");
		//文字
		String text ="啊~~说来话长...那是一个风和日丽的下午，那里茫茫的大草原上有着可爱的生病的小鹿，天空中飘着白色的云彩，一会变成牛，一会变成羊.那天一只奶牛点燃了我心中的怒火,380,100,150,327,20,systemData/fonts/STCAIYUN.TTF,blue";
		TextInfo textInfo = new TextInfo(text);
		info.setText(textInfo);
		info.setUserId("2");
		return info;
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MagickImage image = null;
//		ServletOutputStream out = response.getOutputStream();

		try {
			//------------------------------------------输入参数----------------------------------------//
			// 对象
			String json =request.getParameter("json");
			Gson gson = new Gson();
			TemplateMmsInfo uu = gson.fromJson(json, new TypeToken<TemplateMmsInfo>() {}.getType());

			
			if(json==null||"".equals(json)){
				uu = initData();
			}
			
			uu.setPostcard(FilePathConstants.absoluteAddress(uu.getPostcard()));
			TextInfo textInfo = uu.getText();
			if(textInfo!=null){
				String tmp = FilePathConstants.absoluteAddress(textInfo.getFontsTTF());
				textInfo.setFontsTTF(tmp);
			}
			
			//输出的图片
			String imgPath = System.currentTimeMillis()+".gif";
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");
			String date  = df.format(new Date());
			String fileDir = FilePathConstants.UserDataFilePath+date+"/";
			File f = new File(fileDir);
			if(!f.exists()){
				f.mkdir();
			}
			String toImg = fileDir +imgPath ;
			
			image = MagickDraw.templateMms(uu , toImg);
			
			PrintWriter out = response.getWriter();  
			if(image!=null){
				String callback = request.getParameter("callback");
				CompositeConfiguration c = Config.getConfig();
				String resourcesPath = c.getString("resourcesPath");
				String httpImg = resourcesPath +UrlPathConstants.UserDataUrlPath+date+"/"+ imgPath;
				String jsson = "{\"urls\":\""+httpImg+"\"}";
				response.setContentType("application/json");
				out.print(callback + "(" + jsson + ")");
			}else{
				out.print("null");
			}
			out.flush();
			out.close();
			/*System.out.println("out:"+toImg);
			byte data[] = image.imageToBlob(new ImageInfo());
			out.write(data);
			out.flush();
			out.close();*/
		} catch (Exception e) {
//			e.printStackTrace();
//			PrintWriter out = response.getWriter();  
//			out.print(e.getMessage());
//			out.print(e);
//			out.flush();
//			out.close();
			PrintWriter out = response.getWriter();  
//			out.print(e.getMessage());
//			out.print(e);
//			out.flush();
//			out.close();
			//获取回调函数名
			String callback = request.getParameter("callback");
			//json数据
			String json = "{\"urls\":\"http://211.91.224.244/resources/userData/2013-10/1382785775461.png\"}";
			response.setContentType("application/json");
			//输出：回调函数名(json数据)
			out.print(callback + "(" + json + ")");
		/*	try{
				image.setImageFormat("png");
				byte data[] = image.imageToBlob(new ImageInfo());
				out.write(data);
				out.flush();
				out.close();
			
			}catch(Exception e1){
				e1.printStackTrace();
			}*/
		}
	}
}