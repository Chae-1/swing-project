package com.booklink.model.book;

import com.booklink.model.categories.Categories;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
    private String summary;
    private String description;

    private Integer price;
    private String publisher;
    private Integer salesPoint;
    private Double rating;
    private String imageUrl;
    private Categories categories;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    private Book(BookBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.publicationDate = builder.publicationDate;
        this.summary = builder.summary;
        this.description = builder.description;
        this.price = builder.price;
        this.publisher = builder.publisher;
        this.rating = builder.rating;
        this.salesPoint = builder.salesPoint;
        this.imageUrl = builder.imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public String getRating() {
        return String.valueOf(rating);
    }

    public Long getId() {
        return id;
    }


    public static class BookBuilder {
        private Long id;
        private String imageUrl;
        private String title;
        private String author;
        private LocalDate publicationDate;
        private String summary;
        private String description;
        private Integer price;
        private Double rating;
        private String publisher;
        private Integer salesPoint;

        public BookBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder rating(double rating) {
            this.rating = rating;
            return this;
        }

        public BookBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder publicationDate(LocalDate publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }

        public BookBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public BookBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BookBuilder price(Integer price) {
            this.price = price;
            return this;
        }

        public BookBuilder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookBuilder salesPoint(Integer salesPoint) {
            this.salesPoint = salesPoint;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationDate=" + publicationDate +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", publisher='" + publisher + '\'' +
                ", salesPoint=" + salesPoint +
                ", rating=" + rating +
                ", categories=" + categories +
                '}';
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public Integer getSalesPoint() {
        return salesPoint;
    }

    public Categories getCategories() {
        return categories;
    }
}
