package com.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.domain.ChapterInfo;
import com.domain.NoteInfo;

public class Utils {
	//��ȡhtml��doc   @1			
	public static Document GetDocument(String url) throws IOException {
		Document doc=Jsoup.connect(url).get();
		return doc;
	}
	/*
	 * ��ȡÿ��С˵����Ϣ
	 */

	//��ȡС˵���������а�ҳ���url     @2		����@1
	public static List<NoteInfo> getNotename(Document doc) {
		List<NoteInfo> notelist=new ArrayList<NoteInfo>();
		Element element=doc.select("ul.item-con").first();
		Elements elements=element.select("a[href*=index.html]");
		NoteInfo eachNoteInfo;
		for(Element e:elements){
			eachNoteInfo=new NoteInfo();
			eachNoteInfo.setBaseUrl(e.attr("href"));
			eachNoteInfo.setNoteName(e.text());
			eachNoteInfo.setNoteAuthor(e.parent().parent().select("span.s3").text());
			notelist.add(eachNoteInfo);
			}
		return notelist;
	}
	 
	//����С˵ÿ���½���Ϣ.     ���ֱ��������WriteToTxt���ˡ�
	public static List<ChapterInfo> getChapterInfo(List<NoteInfo> notelist) throws IOException {
		List<ChapterInfo> chapList=new ArrayList<ChapterInfo>();
		for(NoteInfo note:notelist){
			
			//��ȡindexҳ����ڵ�
			Document document=GetDocument(note.getBaseUrl());
			
			//��ȡ�½���Ϣ�ĸ��ڵ���ȡ�½ڽڵ㡣
			Elements element=document.select("dl.chapterlist").first().select("a[href$=.html]");
			
			//ƴ��chapterUrl�ַ�����
			String url1=note.getBaseUrl().replace("index.html", "");
			String url2=url1;
			
			for(Element e:element){
				ChapterInfo cInfo=new ChapterInfo();
				url2=url1;
				url2+=e.attr("href");
				
				//��chapterUrl��chapterName��ֵ
				cInfo.setChapterName(e.text());
				cInfo.setChapterUrl(url2);
				
				//��ȡchapterText
				Document document2=GetDocument(url2);
				
				//����txt�ı��ĸ�ʽ��
				String list="  ";
				Element element3=document2.getElementById("BookText");
				String[] s=element3.text().split(" ");
				for(String l:s){
					list+=l+"\r\n  ";
					cInfo.setChapterText(list);
				}
				chapList.add(cInfo);
			}
			System.out.println("finish");
		}
		
		return chapList;
	}	
}
