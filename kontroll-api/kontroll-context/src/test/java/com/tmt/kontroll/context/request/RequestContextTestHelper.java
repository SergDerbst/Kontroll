package com.tmt.kontroll.context.request;

import java.util.ArrayList;
import java.util.HashMap;

import com.tmt.kontroll.annotations.RequestContext;
import com.tmt.kontroll.context.global.GlobalContextDto;

public class RequestContextTestHelper {

	public static class TestGlobalContextDto implements GlobalContextDto {};

	public static class TestRequestContextDto implements RequestContextDto {
		private String										string;
		private ArrayList<String>					list;
		private HashMap<String, Integer>	map;

		public void setList(final ArrayList<String> list) {
			this.list = list;
		}

		public ArrayList<String> getList() {
			return this.list;
		}

		public HashMap<String, Integer> getMap() {
			return this.map;
		}

		public void setMap(final HashMap<String, Integer> map) {
			this.map = map;
		}

		public String getString() {
			return this.string;
		}

		public void setString(final String string) {
			this.string = string;
		}
	};

	@RequestContext(patterns = {pattern})
	public static class TestRequestContextService extends RequestContextService<TestRequestContextDto, TestGlobalContextDto> {

		@Override
		public TestRequestContextDto find(final TestGlobalContextDto data) {
			return new TestRequestContextDto();
		}
	}

	public static final String	pattern	= "pattern";
}
