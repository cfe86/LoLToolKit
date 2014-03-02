package model.util;

public interface Tag {

	public final int BOLD = 0;
	public final String BOLD_START = "[B]";
	public final String BOLD_END = "[/B]";

	public final int ITALIC = 1;
	public final String ITALIC_START = "[I]";
	public final String ITALIC_END = "[/I]";

	public final int UNDERLINE = 2;
	public final String UNDERLINE_START = "[U]";
	public final String UNDERLINE_END = "[/U]";

	public final int LISTNR = 3;
	public final String LISTNR_START = "[LIST=0]";
	public final String LISTNR_END = "[/LIST]";

	public final int LISTUNNR = 4;
	public final String LISTUNNR_START = "[LIST=1]";
	public final String LISTUNNR_END = "[/LIST]";

	public final int ITEM = 5;
	public final String ITEM_START = "[ITEM]";
	public final String ITEM_END = "[/ITEM]";
}
