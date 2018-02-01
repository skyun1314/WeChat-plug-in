package com.oppo.cdo.card.domain.dto;
import com.oppo.cdo.card.domain.dto.BannerDto;
import com.oppo.cdo.common.domain.dto.ResourceDto;
import com.oppo.lr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: ViewCheckerWrapper */
public class xt extends lu {



    /* compiled from: IExposureInfoGetter */
    public interface xs {
        List<c> a();
    }


    public int c;
    public String d;
    public xs e;

    public xt(int i, String str, int i2, xs xsVar) {
        super(i, null);
        this.d = str;
        this.c = i2;
        this.e = xsVar;
    }

    public List<lr> a() {
        if (this.e == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (c cVar : this.e.a()) {
            if (cVar.f != null) {
                for (c.a aVar : cVar.f) {
                    arrayList.add(new lr(this.d, this.c, cVar.b, cVar.c, cVar.d, aVar.a.getVerId(), aVar.a.getCatLev3(), aVar.b, aVar.a.getAdId(), aVar.a.getAdPos(), aVar.a.getAdContent(), aVar.a.getSrcKey()));
                }
            }
            if (cVar.e != null && 1004 == cVar.b) {
                for (c.b bVar : cVar.e) {
                    BannerDto bannerDto = bVar.a;
                    int i = bVar.b;
                    if (bannerDto != null) {
                        lr lrVar = new lr(this.d, this.c, cVar.b, cVar.c, cVar.d, (long) bannerDto.getId(), 0, bVar.b, 0, null, null, null);
                        lrVar.q = 3;
                        arrayList.add(lrVar);
                        if (true) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("[id = ").append(bannerDto.getId()).append("]");
                            stringBuffer.append("[image = ").append(bannerDto.getImage()).append("]");
                            stringBuffer.append("[actionType = ").append(bannerDto.getActionType()).append("]");
                            stringBuffer.append("[actionParam = ").append(bannerDto.getActionParam()).append("]");
                            stringBuffer.append("[title = ").append(bannerDto.getTitle()).append("]");
                            stringBuffer.append("[desc = ").append(bannerDto.getDesc()).append("]");
                            stringBuffer.append("[time = ").append(bannerDto.getTime()).append("]");
                            stringBuffer.append("[posInCard = ").append(i).append("]");
                          //  je.a("banner_exp", stringBuffer.toString());
                        }
                    } else {
                      //  je.a("banner_exp", "banner dto is null, posInCard = " + i);
                    }
                }
            }
        }
        return arrayList;
    }
}