#include "util.h"
#include <vector>
#include <algorithm>
#include <android/log.h>

namespace MTC{	namespace Util{
#if  defined (ANDROID)
	const char* fontName[] = { 
		"/data/data/com.almas.mtc/files/MongolianWhite.ttf",
		"/data/data/com.almas.mtc/files/msyh.ttf"
	};
#else
	const char* fontName[] = { "../MongolianWhite.ttf", "../msyh.ttf" };
#endif
	int FontOption::Ascender() const
	{
		return std::max(ft_face[0]->size->metrics.ascender, ft_face[1]->size->metrics.ascender) >> 6;
	}
	int FontOption::Descender() const
	{
		return std::min(ft_face[0]->size->metrics.descender, ft_face[1]->size->metrics.descender) >> 6;
	}
	int FontOption::LineHeight() const
	{
		/*
		int ad_height0 = ft_face[0]->max_advance_height >> 6;
		int height0 = ft_face[0]->height >> 6;

		int ad_height1 = ft_face[1]->max_advance_height >> 6;
		int height1 = ft_face[1]->height >> 6;
		*/
		int max_ascender = std::max(ft_face[0]->size->metrics.ascender, (FT_Pos)ft_face[1]->size->metrics.ascender) >> 6;
		int min_descender = std::min(ft_face[0]->size->metrics.descender, (FT_Pos)ft_face[1]->size->metrics.descender) >> 6;

		/*
		http://lists.gnu.org/archive/html/freetype/2001-08/msg00066.html
		*/
		int line_gap0 = (ft_face[0]->size->metrics.height - ft_face[0]->size->metrics.ascender) >> 6;
		int line_gap1 = (ft_face[1]->size->metrics.height - ft_face[1]->size->metrics.ascender) >> 6;
		return max_ascender - min_descender + std::max(line_gap0, line_gap1);
	}
	FontOption::FontOption(int size, int fore_, int back_)
	{
		__android_log_print(2, "MTC", "FontOption - start size=%d", size);
		FT_Init_FreeType(&ft_library); /* initialize library */
		fore = fore_;
		back = back_;
		__android_log_print(2, "MTC", "FontOption - start 2");
		//GetFullPathName(fontName[0], MAX_PATH, fullPath, 0);
		for (int i = 0; i < 2; i++)
		{
			__android_log_print(2, "MTC", "FontOption - start loopi=%d", i);
			FT_Error error = FT_New_Face(ft_library, fontName[i], 0, &ft_face[i]); /* create face object */
			if(error != 0)
			{
				__android_log_print(2, "MTC", "FontOption - failed to create font %s", fontName[i]);
			}
			FT_Set_Pixel_Sizes(ft_face[i], 0, size); /* set character size */
			hb_ft_font[i] = hb_ft_font_create(ft_face[i], NULL);
		}
		__android_log_print(2, "MTC", "FontOption - end");
	}
	FontOption::~FontOption()
	{
		for (int i = 0; i < 2; i++)
		{
			hb_font_destroy(hb_ft_font[i]);
			FT_Done_Face(ft_face[i]);
		}
		FT_Done_FreeType(ft_library);
	}
	int FontOption::font_index_from_script(hb_script_t script) const
	{
		if (script == HB_SCRIPT_MONGOLIAN)
			return MONGOL;
		else
			return OTHER;
	}
}}
