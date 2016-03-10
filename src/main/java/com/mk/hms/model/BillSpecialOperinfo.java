package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.Date;

public class BillSpecialOperinfo {
    private Long id;

    private Long specialId;

    private BigDecimal billcost;

    private String checkstatus;

    private String checkstatusname;

    private String financestatus;

    private String financestatusname;

    private BigDecimal changemoney;

    private String note;

    private Long checkuserid;

    private String checkusername;

    private Date checktime;

    private Long backuserid;

    private String backusername;

    private Date backtime;

    private String backmemo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }

    public BigDecimal getBillcost() {
        return billcost;
    }

    public void setBillcost(BigDecimal billcost) {
        this.billcost = billcost;
    }

    public String getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(String checkstatus) {
        this.checkstatus = checkstatus == null ? null : checkstatus.trim();
    }

    public String getCheckstatusname() {
        return checkstatusname;
    }

    public void setCheckstatusname(String checkstatusname) {
        this.checkstatusname = checkstatusname == null ? null : checkstatusname.trim();
    }

    public String getFinancestatus() {
        return financestatus;
    }

    public void setFinancestatus(String financestatus) {
        this.financestatus = financestatus == null ? null : financestatus.trim();
    }

    public String getFinancestatusname() {
        return financestatusname;
    }

    public void setFinancestatusname(String financestatusname) {
        this.financestatusname = financestatusname == null ? null : financestatusname.trim();
    }

    public BigDecimal getChangemoney() {
        return changemoney;
    }

    public void setChangemoney(BigDecimal changemoney) {
        this.changemoney = changemoney;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Long getCheckuserid() {
        return checkuserid;
    }

    public void setCheckuserid(Long checkuserid) {
        this.checkuserid = checkuserid;
    }

    public String getCheckusername() {
        return checkusername;
    }

    public void setCheckusername(String checkusername) {
        this.checkusername = checkusername == null ? null : checkusername.trim();
    }

    public Date getChecktime() {
        return checktime;
    }

    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

    public Long getBackuserid() {
        return backuserid;
    }

    public void setBackuserid(Long backuserid) {
        this.backuserid = backuserid;
    }

    public String getBackusername() {
        return backusername;
    }

    public void setBackusername(String backusername) {
        this.backusername = backusername == null ? null : backusername.trim();
    }

    public Date getBacktime() {
        return backtime;
    }

    public void setBacktime(Date backtime) {
        this.backtime = backtime;
    }

    public String getBackmemo() {
        return backmemo;
    }

    public void setBackmemo(String backmemo) {
        this.backmemo = backmemo == null ? null : backmemo.trim();
    }
}