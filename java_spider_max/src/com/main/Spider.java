package com.main;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.nodes.Document;

import com.domain.NoteInfo;
import com.utils.Utils;

public class Spider {

	public static void main(String[] args) throws IOException {
		List<NoteInfo> noteList = new ArrayList<NoteInfo>();
		Document doc = Utils.GetDocument("http://www.kanshuge.la/files/article/topallvote/0/2.htm");
		noteList = Utils.getNotename(doc);
		System.out.println(noteList.size());
		ExecutorService pool = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 100; i++) {
			pool.execute(new Thread(new WriteToTxt(noteList.get(i))));
		}
		pool.shutdown();
	}
}
