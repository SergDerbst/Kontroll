package com.tmt.kontroll.context.request;

import java.util.ArrayList;
import java.util.HashMap;

import com.tmt.kontroll.annotations.RequestContext;
import com.tmt.kontroll.context.global.GlobalContextDto;

public class RequestContextTestHelper {

	public static class TestGlobalContextDto implements GlobalContextDto {};
	public static class TestRequestContextDto implements RequestContextDto {
		private String string;
		private ArrayList<String> list;
		private HashMap<String, Integer> map;
		public void setList(ArrayList<String> list) {
			this.list = list;
		}
		public ArrayList<String> getList() {
			return list;
		}
		public HashMap<String, Integer> getMap() {
			return map;
		}
		public void setMap(HashMap<String, Integer> map) {
			this.map = map;
		}
		public String getString() {
			return string;
		}
		public void setString(String string) {
			this.string = string;
		}
	};
	@RequestContext(patterns = {pattern})
	public static class TestRequestContextService extends RequestContextService<TestRequestContextDto, TestGlobalContextDto> {

		@Override
		public TestRequestContextDto find(TestGlobalContextDto data) {
			return new TestRequestContextDto();
		}
	}
	
	public static final String pattern = "pattern";
}
