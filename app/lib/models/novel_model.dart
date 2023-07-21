class NovelModel {
  final String title, plot, thumb, id;

  NovelModel.fromJson(Map<String, dynamic> json)
      : title = json['title'],
        // plot = json['plot'],
        plot = "줄거리",
        thumb = json['thumb'],
        id = json['id'];
}
