package inc.satyam.attendancemanager;

import android.os.Parcel;
import android.os.Parcelable;

public class SubjectModel implements Parcelable {
    String subject, code, totalClasses, professor, branch, roll, sem, year;

    protected SubjectModel(Parcel in) {
        subject = in.readString();
        code = in.readString();
        totalClasses = in.readString();
        professor = in.readString();
        branch = in.readString();
        roll = in.readString();
        sem = in.readString();
        year = in.readString();
    }

    public static final Creator<SubjectModel> CREATOR = new Creator<SubjectModel>() {
        @Override
        public SubjectModel createFromParcel(Parcel in) {
            return new SubjectModel(in);
        }

        @Override
        public SubjectModel[] newArray(int size) {
            return new SubjectModel[size];
        }
    };

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public SubjectModel(String subject, String code, String totalClasses, String professor, String branch, String roll, String sem, String year) {
        this.subject = subject;
        this.code = code;
        this.totalClasses = totalClasses;
        this.professor = professor;
        this.branch = branch;
        this.roll = roll;
        this.sem = sem;
        this.year = year;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(String totalClasses) {
        this.totalClasses = totalClasses;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public SubjectModel(String subject, String code, String totalClasses, String professor) {
        this.subject = subject;
        this.code = code;
        this.totalClasses = totalClasses;
        this.professor = professor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subject);
        parcel.writeString(code);
        parcel.writeString(totalClasses);
        parcel.writeString(professor);
        parcel.writeString(branch);
        parcel.writeString(roll);
        parcel.writeString(sem);
        parcel.writeString(year);
    }
}
