import 'dart:convert';

import 'package:http/http.dart' as http;

import 'package:app/models/novel_model.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

class ApiService {
  static final String baseUrl = dotenv.env['novelListUrl'].toString();
  static const String today = "today";

  static Future<List<NovelModel>> getTodaysNovel() async {
    List<NovelModel> novelInstances = [];
    final url = Uri.parse('$baseUrl/$today');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final novels = jsonDecode(response.body);
      for (var novel in novels) {
        novelInstances.add(NovelModel.fromJson(novel));
      }
      return novelInstances;
    }
    throw Error();
  }
}
